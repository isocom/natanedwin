/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appspot.natanedwin.dao;

import com.appspot.natanedwin.app.AppError;
import com.appspot.natanedwin.entity.Establishment;
import com.appspot.natanedwin.service.ofy.Ofy;
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
    public void delete(Establishment entity) {
        throw new AppError("Can't delete " + entity.getClass().getSimpleName(), "Nie można usuwać tego typu obiektów");
    }

    @Override
    public void save(Establishment e) {
        ofy.ofy().save().entity(e).now();
    }
}
