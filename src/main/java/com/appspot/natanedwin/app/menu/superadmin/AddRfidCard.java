package com.appspot.natanedwin.app.menu.superadmin;

import com.appspot.natanedwin.entity.RfidCard;
import com.appspot.natanedwin.vaadin.EntityItemWindow;
import com.appspot.natanedwin.vaadin.entity.RfidCardItem;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;

/**
 *
 * @author prokob01
 */
public class AddRfidCard implements MenuBar.Command {

    static final long serialVersionUID = -2260999470984426045L;

    @Override
    public void menuSelected(MenuItem selectedItem) {
        EntityItemWindow.showWindow(new RfidCardItem(new RfidCard()));
    }
}
