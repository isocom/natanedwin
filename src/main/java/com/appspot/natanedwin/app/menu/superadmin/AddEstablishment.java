package com.appspot.natanedwin.app.menu.superadmin;

import com.appspot.natanedwin.entity.Establishment;
import com.appspot.natanedwin.vaadin.EntityItemWindow;
import com.appspot.natanedwin.vaadin.entity.EstablishmentItem;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;

/**
 *
 * @author prokob01
 */
public class AddEstablishment implements MenuBar.Command {

    @Override
    public void menuSelected(MenuItem selectedItem) {
        EntityItemWindow.showWindow(new EstablishmentItem(new Establishment()));
    }
}
