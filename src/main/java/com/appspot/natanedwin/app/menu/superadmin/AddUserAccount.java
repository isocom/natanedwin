package com.appspot.natanedwin.app.menu.superadmin;

import com.appspot.natanedwin.entity.UserAccount;
import com.appspot.natanedwin.vaadin.EntityItemWindow;
import com.appspot.natanedwin.vaadin.entity.UserAccountItem;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;

/**
 *
 * @author prokob01
 */
public class AddUserAccount implements MenuBar.Command {

    static final long serialVersionUID = -1759938533921722524L;

    @Override
    public void menuSelected(MenuItem selectedItem) {
        EntityItemWindow.showWindow(new UserAccountItem(new UserAccount()));
    }
}
