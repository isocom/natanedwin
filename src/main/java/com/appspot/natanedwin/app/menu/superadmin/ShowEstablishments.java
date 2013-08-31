/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appspot.natanedwin.app.menu.superadmin;

import com.appspot.natanedwin.entity.Establishment;
import com.appspot.natanedwin.service.ofy.Ofy;
import com.appspot.natanedwin.service.spring.SpringContext;
import com.appspot.natanedwin.vaadin.EntityContainerWindow;
import com.appspot.natanedwin.vaadin.EntityContainer;
import com.appspot.natanedwin.vaadin.entity.EstablishmentItem;
import com.googlecode.objectify.cmd.Query;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import java.util.List;

/**
 *
 * @author prokob01
 */
public class ShowEstablishments implements MenuBar.Command {

    @Override
    public void menuSelected(MenuItem selectedItem) {
        Ofy ofy = SpringContext.INSTANCE.getBean(Ofy.class);
        Query<Establishment> query = ofy.ofy().load().type(Establishment.class);
        List<Establishment> list = query.list();
        ofy.ofy().clear();

        EntityContainerWindow.showWindow(new EntityContainer<Establishment>(list, EstablishmentItem.class));
    }
}
