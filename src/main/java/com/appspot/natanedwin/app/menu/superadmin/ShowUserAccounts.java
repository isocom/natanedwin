package com.appspot.natanedwin.app.menu.superadmin;

import com.appspot.natanedwin.dao.UserAccountDao;
import com.appspot.natanedwin.service.spring.SpringContext;
import com.appspot.natanedwin.vaadin.EntityContainerWindow;
import com.appspot.natanedwin.vaadin.EntityContainer;
import com.appspot.natanedwin.vaadin.entity.UserAccountItem;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;

/**
 *
 * @author prokob01
 */
public class ShowUserAccounts implements MenuBar.Command {

    @Override
    public void menuSelected(MenuItem selectedItem) {
        UserAccountDao userAccountDao = SpringContext.INSTANCE.getBean(UserAccountDao.class);
        EntityContainerWindow.showWindow(new EntityContainer<>(userAccountDao.findAll(), UserAccountItem.class));
    }
}
