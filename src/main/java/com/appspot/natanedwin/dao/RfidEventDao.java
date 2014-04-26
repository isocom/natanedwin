package com.appspot.natanedwin.dao;

import com.appspot.natanedwin.app.AppError;
import com.appspot.natanedwin.entity.Human;
import com.appspot.natanedwin.entity.RfidEvent;
import com.appspot.natanedwin.service.ofy.Ofy;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.cmd.Query;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author prokob01
 */
@Repository
public class RfidEventDao implements Dao<RfidEvent> {

    private static final Logger logger = LoggerFactory.getLogger(RfidEventDao.class);

    @Autowired
    private Ofy ofy;

    @Override
    public RfidEvent byId(long id) {
        return ofy.ofy().load().type(RfidEvent.class).id(id).safe();
    }

    @Override
    public RfidEvent byRef(Ref<RfidEvent> ref) {
        return ofy.ofy().load().now(ref.getKey());
    }

    public List<RfidEvent> findAll() {
        Query<RfidEvent> query = ofy.ofy().load().type(RfidEvent.class);
        List<RfidEvent> list = query.list();
        return list;
    }

    public List<RfidEvent> find(DateTime date, List<Human> humans) {
        date = date.withTimeAtStartOfDay();
        Date from = date.toDate();
        Date to = new Date(date.getMillis() + 24 * 60 * 60 * 1000);
        List<RfidEvent> list = ofy.ofy().load().type(RfidEvent.class).filter("eventDate > ", from).filter("eventDate < ", to).filter("human in", humans).list();
        return new ArrayList<>(list);
    }

    public List<RfidEvent> find(DateTime from, DateTime to, List<Human> humans) {
        //Date f = new Date(from.toDate().getTime() + 24L * 1000 * 60 * 60 * 10);
        Date f = from.toDate();
        Date t = to.toDate();
        List<RfidEvent> list = ofy.ofy().load().type(RfidEvent.class).filter("eventDate > ", f).filter("eventDate < ", t).filter("human in", humans).list();
        logger.error("Raport za " + f + " - " + t + " zawiera: " + list.size());
        logger.error("Ostatni: " + list.get(list.size() - 1).getEventDate());
        return new ArrayList<>(list);
    }

    @Override
    public RfidEvent delete(RfidEvent entity) {
        throw new AppError("Can't delete " + entity.getClass().getSimpleName(), "Nie można usuwać tego typu obiektów");
    }

    @Override
    public void delete(long id) {
        ofy.ofy().delete().type(RfidEvent.class).id(id);
    }

    @Override
    public RfidEvent save(RfidEvent event) {
        ofy.ofy().save().entity(event);
        return event;
    }
}
