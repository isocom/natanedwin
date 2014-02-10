package com.appspot.natanedwin.dao;

import com.appspot.natanedwin.app.AppError;
import com.appspot.natanedwin.entity.Human;
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
public class HumanDao implements Dao<Human> {

    @Autowired
    private Ofy ofy;

    @Override
    public Human byId(long id) {
        return ofy.ofy().load().type(Human.class).id(id).safe();
    }

    @Override
    public Human byRef(Ref<Human> ref) {
        return ofy.ofy().load().now(ref.getKey());
    }

    @Override
    public Human delete(Human entity) {
        throw new AppError("Can't delete " + entity.getClass().getSimpleName(), "Nie można usuwać tego typu obiektów");
    }

    public List<Human> findAll() {
        Query<Human> query = ofy.ofy().load().type(Human.class);
        List<Human> list = query.list();
        ofy.ofy().clear();
        return list;
    }

    public List<Human> findRecent(int limit) {
        return ofy.ofy().load().type(Human.class).limit(limit).order("-firstTimeSeen").list();
    }

    @Override
    public Human save(Human e) {
        if (e.getName() == null) {
            throw new AppError("Can't save when name is empty", "Bład zapisu do BigTable");
        }
        ofy.ofy().save().entity(e).now();
        return e;
    }
}
