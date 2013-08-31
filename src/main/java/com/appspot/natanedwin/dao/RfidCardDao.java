package com.appspot.natanedwin.dao;

import com.appspot.natanedwin.app.AppError;
import com.appspot.natanedwin.entity.Human;
import com.appspot.natanedwin.entity.RfidCard;
import com.appspot.natanedwin.service.memcache.MemCache;
import com.appspot.natanedwin.service.ofy.Ofy;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.cmd.Query;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author prokob01
 */
@Repository
public class RfidCardDao implements Dao<RfidCard> {

    @Autowired
    private Ofy ofy;
    @Autowired
    private MemCache memCache;
    private static final String MC_SN_PREFIX = "RfidCard-SN-";

    @Override
    public RfidCard byId(long id) {
        return ofy.ofy().load().type(RfidCard.class).id(id).safe();
    }

    public List<RfidCard> findAll() {
        Query<RfidCard> query = ofy.ofy().load().type(RfidCard.class);
        List<RfidCard> list = query.list();
        ofy.ofy().clear();
        return list;
    }

    public RfidCard findBySerialNumber(final String serialNumber) {
        RfidCard retVal = memCache.get(MC_SN_PREFIX + serialNumber, RfidCard.class);
        if (retVal != null) {
            return retVal;
        }
        retVal = ofy.ofy().load().type(RfidCard.class).filter("serialNumber = ", serialNumber).first().now();
        return retVal;
    }

    public List<RfidCard> findHumanUnassigned() {
        return ofy.ofy().load().type(RfidCard.class).filter("human == ", null).list();
    }

    public void assignHuman(RfidCard rfidCard, Human human) {
        Objectify objectify = ofy.ofy();
        rfidCard = objectify.load().entity(rfidCard).get();
        human = objectify.load().entity(human).get();
        rfidCard.setHumanValue(human);
        rfidCard.setRemarks("Karta MetalSprzęt");
        objectify.save().entity(rfidCard);
    }

    @Override
    public void delete(final RfidCard entity) {
        throw new AppError("Can't delete " + entity.getClass().getSimpleName(), "Nie można usuwać tego typu obiektów");
    }

    @Override
    public void save(final RfidCard card) {
        ofy.ofy().save().entity(card).now();
        memCache.put(MC_SN_PREFIX + card.getSerialNumber(), card);
    }
}
