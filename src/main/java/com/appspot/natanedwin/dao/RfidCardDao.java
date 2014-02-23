package com.appspot.natanedwin.dao;

import com.appspot.natanedwin.app.AppError;
import com.appspot.natanedwin.entity.GcsFile;
import com.appspot.natanedwin.entity.Human;
import com.appspot.natanedwin.entity.RfidCard;
import com.appspot.natanedwin.entity.RfidCardType;
import com.appspot.natanedwin.service.memcache.MemCache;
import com.appspot.natanedwin.service.ofy.Ofy;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.Ref;
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

    @Override
    public RfidCard byRef(Ref<RfidCard> ref) {
        return ofy.ofy().load().now(ref.getKey());
    }

    public List<RfidCard> findAll() {
        Query<RfidCard> query = ofy.ofy().load().type(RfidCard.class);
        List<RfidCard> list = query.list();
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

    public RfidCard findByCardNumber(final String cardNumber) {
//        RfidCard retVal = memCache.get(MC_SN_PREFIX + serialNumber, RfidCard.class);
//        if (retVal != null) {
//            return retVal;
//        }
        RfidCard retVal = ofy.ofy().load().type(RfidCard.class).filter("cardNumber = ", cardNumber).first().now();
        return retVal;
    }

    public List<RfidCard> findHumanUnassigned() {
        return ofy.ofy().load().type(RfidCard.class).filter("human == ", null).list();
    }

    public List<RfidCard> findByHuman(Human human) {
        return ofy.ofy().load().type(RfidCard.class).filter("human == ", human).list();
    }

    public List<RfidCard> findRecent(int limit) {
        return ofy.ofy().load().type(RfidCard.class).limit(limit).order("-firstTimeSeen").list();
    }

    public void assignHuman(RfidCard rfidCard, Human human) {
        Objectify objectify = ofy.ofy();
        rfidCard = objectify.load().entity(rfidCard).get();
        human = objectify.load().entity(human).get();
        rfidCard.setHuman(human);
        objectify.save().entity(rfidCard);
    }

    public void assignOverprint(RfidCard rfidCard, GcsFile overprint) {
        Objectify objectify = ofy.ofy();
        rfidCard = objectify.load().entity(rfidCard).now();
        overprint = objectify.load().entity(overprint).now();
        rfidCard.setOverprint(overprint);
        objectify.save().entity(rfidCard);
    }

    @Override
    public RfidCard delete(final RfidCard entity) {
        if (entity.getRfidCardType() == RfidCardType.Vacant) {
            ofy.ofy().delete().entity(entity);
            return entity;
        }
        if (entity.getRfidCardType() == RfidCardType.Mifare1k && entity.getHuman() == null) {
            ofy.ofy().delete().entity(entity);
            return entity;
        }
        throw new AppError("Can't delete " + entity.getClass().getSimpleName(), "Nie można usuwać tego typu obiektów");
    }

    @Override
    public void delete(long id) {
        throw new AppError("Can't delete " + id, "Nie można usuwać tego typu obiektów");
    }

    @Override
    public RfidCard save(final RfidCard card) {
        ofy.ofy().save().entity(card).now();
        memCache.put(MC_SN_PREFIX + card.getSerialNumber(), card);
        return card;
    }
}
