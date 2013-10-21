/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appspot.natanedwin.app.menu.superadmin;

import com.appspot.natanedwin.dao.RfidCardDao;
import com.appspot.natanedwin.entity.RfidCard;
import com.appspot.natanedwin.service.spring.SpringContext;
import com.appspot.natanedwin.vaadin.EntityAction;
import com.appspot.natanedwin.vaadin.EntityContainerWindow;
import com.appspot.natanedwin.vaadin.EntityContainer;
import com.appspot.natanedwin.vaadin.entity.RfidCardItem;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author prokob01
 */
public class ShowRfidCards implements MenuBar.Command {

    private Map<String, EntityAction> additionalActions = new LinkedHashMap<>();

    public ShowRfidCards() {
        additionalActions.put("Drukuj kartę", new PrintCard());
    }

    @Override
    public void menuSelected(MenuItem selectedItem) {
        RfidCardDao rfidCardDao = SpringContext.INSTANCE.getBean(RfidCardDao.class);
        EntityContainerWindow.showWindow(new EntityContainer<>(rfidCardDao.findAll(), RfidCardItem.class), additionalActions);
    }
}
