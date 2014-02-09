package com.appspot.natanedwin.dao;

import com.appspot.natanedwin.app.AppError;
import com.appspot.natanedwin.entity.StatEntry;
import com.appspot.natanedwin.service.ofy.Ofy;
import com.googlecode.objectify.Ref;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author prokob01
 */
@Repository
public class StatEntryDao implements Dao<StatEntry> {

    @Autowired
    private Ofy ofy;

    @Override
    public StatEntry byId(long id) {
        return ofy.ofy().load().type(StatEntry.class).id(id).safe();
    }

    @Override
    public StatEntry byRef(Ref<StatEntry> ref) {
        return ofy.ofy().load().now(ref.getKey());
    }

    @Override
    public StatEntry delete(StatEntry entity) {
        throw new AppError("Can't delete " + entity.getClass().getSimpleName(), "Nie można usuwać tego typu obiektów");
    }

    @Override
    public StatEntry save(StatEntry e) {
        ofy.ofy().save().entity(e);
        return e;
    }
}
