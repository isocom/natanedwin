package com.appspot.natanedwin.dao;

import com.appspot.natanedwin.app.AppError;
import com.appspot.natanedwin.entity.GcsFile;
import com.appspot.natanedwin.service.gcs.Gcs;
import com.appspot.natanedwin.service.gcs.GcsMimeType;
import com.appspot.natanedwin.service.ofy.Ofy;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.cmd.Query;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class GcsFileDao implements Dao<GcsFile> {

    @Autowired
    private Ofy ofy;
    @Autowired
    private Gcs gcs;

    @Override
    public GcsFile byId(long id) {
        return ofy.ofy().load().type(GcsFile.class).id(id).safe();
    }

    @Override
    public GcsFile byRef(Ref<GcsFile> ref) {
        return ofy.ofy().load().now(ref.getKey());
    }
    public GcsFile byUUID(final UUID uuid) {
        return byUUID(uuid.toString());
    }

    public GcsFile byUUID(final String uuid) {
        return ofy.ofy().load().type(GcsFile.class).filter("uuid = ", uuid).first().now();
    }

    @Override
    public GcsFile delete(GcsFile entity) {
        ofy.ofy().delete().entity(entity).now();
        gcs.delete(entity.getBucketName(), entity.getObjectName());
        return entity;
    }

    @Override
    public void delete(long id) {
        throw new AppError("Can't delete " + id, "Nie można usuwać tego typu obiektów");
    }

    @Override
    public GcsFile save(GcsFile e) {
        ofy.ofy().save().entity(e).now();
        return e;
    }

    public GcsFile save(GcsFile e, byte[] content) {
        ofy.ofy().save().entity(e).now();
        gcs.write(e.getBucketName(), e.getObjectName(), GcsMimeType.BMP, content);
        return e;
    }

    public List<GcsFile> findAll() {
        Query<GcsFile> query = ofy.ofy().load().type(GcsFile.class);
        List<GcsFile> list = query.list();
        ofy.ofy().clear();
        return list;
    }
}
