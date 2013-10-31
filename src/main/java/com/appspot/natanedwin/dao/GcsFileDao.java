package com.appspot.natanedwin.dao;

import com.appspot.natanedwin.app.AppError;
import com.appspot.natanedwin.entity.GcsFile;
import com.appspot.natanedwin.service.ofy.Ofy;
import com.googlecode.objectify.cmd.Query;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class GcsFileDao implements Dao<GcsFile> {

    @Autowired
    private Ofy ofy;

    @Override
    public GcsFile byId(long id) {
        return ofy.ofy().load().type(GcsFile.class).id(id).safe();
    }

    @Override
    public GcsFile delete(GcsFile entity) {
        throw new AppError("Can't delete " + entity.getClass().getSimpleName(), "Nie można usuwać tego typu obiektów");
    }

    @Override
    public GcsFile save(GcsFile e) {
        ofy.ofy().save().entity(e).now();
        return e;
    }

    public List<GcsFile> findAll() {
        Query<GcsFile> query = ofy.ofy().load().type(GcsFile.class);
        List<GcsFile> list = query.list();
        ofy.ofy().clear();
        return list;
    }
}
