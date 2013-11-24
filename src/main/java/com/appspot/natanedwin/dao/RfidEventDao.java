/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appspot.natanedwin.dao;

import com.appspot.natanedwin.app.AppError;
import com.appspot.natanedwin.entity.Human;
import com.appspot.natanedwin.entity.RfidEvent;
import com.appspot.natanedwin.service.ofy.Ofy;
import com.googlecode.objectify.cmd.Query;
//import com.googlecode.objectify.Ref;
//import com.googlecode.objectify.impl.ref.NullRef;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author prokob01
 */
@Repository
public class RfidEventDao implements Dao<RfidEvent> {

    @Autowired
    private Ofy ofy;

    @Override
    public RfidEvent byId(long id) {
        return ofy.ofy().load().type(RfidEvent.class).id(id).safe();
    }

    public List<RfidEvent> find() {
        List<RfidEvent> list = ofy.ofy().load().type(RfidEvent.class).limit(2).list();
        list = new ArrayList<>(list);
        return list;
    }

    public List<RfidEvent> findAll() {
        Query<RfidEvent> query = ofy.ofy().load().type(RfidEvent.class);
        List<RfidEvent> list = query.list();
        return list;
    }

    public List<RfidEvent> findToday(DateTime date, List<Human> humans) {
        date = date.withTimeAtStartOfDay();
        Date from = date.toDate();
        Date to = new Date(date.getMillis() + 24 * 60 * 60 * 1000);

        List<RfidEvent> list = ofy.ofy().load().type(RfidEvent.class).filter("eventDate > ", from).filter("eventDate < ", to).filter("human in", humans).list();
        return new ArrayList<>(list);
    }

    @Override
    public RfidEvent delete(RfidEvent entity) {
        throw new AppError("Can't delete " + entity.getClass().getSimpleName(), "Nie można usuwać tego typu obiektów");
    }

    @Override
    public RfidEvent save(RfidEvent event) {
        ofy.ofy().save().entity(event);
        return event;
    }
}
