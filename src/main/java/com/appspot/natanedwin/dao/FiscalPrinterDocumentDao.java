package com.appspot.natanedwin.dao;

import com.appspot.natanedwin.app.AppError;
import com.appspot.natanedwin.entity.FiscalPrinterDocument;
import com.appspot.natanedwin.service.ofy.Ofy;
import com.googlecode.objectify.Ref;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class FiscalPrinterDocumentDao implements Dao<FiscalPrinterDocument> {

    @Autowired
    private Ofy ofy;

    @Override
    public FiscalPrinterDocument byId(long id) {
        return ofy.ofy().load().type(FiscalPrinterDocument.class).id(id).safe();
    }

    @Override
    public FiscalPrinterDocument byRef(Ref<FiscalPrinterDocument> ref) {
        return ofy.ofy().load().now(ref.getKey());
    }
    public FiscalPrinterDocument byUUID(final UUID uuid) {
        return byUUID(uuid.toString());
    }

    public FiscalPrinterDocument byUUID(final String uuid) {
        return ofy.ofy().load().type(FiscalPrinterDocument.class).filter("uuid = ", uuid).first().now();
    }

    @Override
    public FiscalPrinterDocument delete(FiscalPrinterDocument entity) {
        throw new AppError("Can't delete " + entity.getClass().getSimpleName(), "Nie można usuwać tego typu obiektów");
    }

    @Override
    public void delete(long id) {
        throw new AppError("Can't delete " + id, "Nie można usuwać tego typu obiektów");
    }

    @Override
    public FiscalPrinterDocument save(FiscalPrinterDocument entity) {
        ofy.ofy().save().entity(entity).now();
        return entity;
    }

}
