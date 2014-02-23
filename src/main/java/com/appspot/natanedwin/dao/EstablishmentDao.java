package com.appspot.natanedwin.dao;

import com.appspot.natanedwin.app.AppError;
import com.appspot.natanedwin.entity.Establishment;
import com.appspot.natanedwin.service.ofy.Ofy;
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
public class EstablishmentDao implements Dao<Establishment> {

    @Autowired
    private Ofy ofy;

    @Override
    public Establishment byId(long id) {
        return ofy.ofy().load().type(Establishment.class).id(id).safe();
    }

    @Override
    public Establishment byRef(Ref<Establishment> ref) {
        return ofy.ofy().load().now(ref.getKey());
    }

    @Override
    public Establishment delete(Establishment entity) {
        throw new AppError("Can't delete " + entity.getClass().getSimpleName(), "Nie można usuwać tego typu obiektów");
    }

    @Override
    public void delete(long id) {
        throw new AppError("Can't delete " + id, "Nie można usuwać tego typu obiektów");
    }

    @Override
    public Establishment save(Establishment e) {
        ofy.ofy().save().entity(e).now();
        return e;
    }

    public List<Establishment> findAll() {
        Query<Establishment> query = ofy.ofy().load().type(Establishment.class);
        List<Establishment> list = query.list();
        ofy.ofy().clear();
        return list;
    }
}
