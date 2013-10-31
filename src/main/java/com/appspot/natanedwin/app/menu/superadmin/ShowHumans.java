package com.appspot.natanedwin.app.menu.superadmin;

import com.appspot.natanedwin.dao.HumanDao;
import com.appspot.natanedwin.service.spring.SpringContext;
import com.appspot.natanedwin.vaadin.EntityAction;
import com.appspot.natanedwin.vaadin.EntityContainerWindow;
import com.appspot.natanedwin.vaadin.EntityContainer;
import com.appspot.natanedwin.vaadin.entity.HumanItem;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author prokob01
 */
public class ShowHumans implements MenuBar.Command {

    private Map<String, EntityAction> additionalActions = new LinkedHashMap<>();

    public ShowHumans() {
        additionalActions.put("Przypisz kartę", new AssignCardToHuman1());
    }

    @Override
    public void menuSelected(MenuItem selectedItem) {
        HumanDao humanDao = SpringContext.INSTANCE.getBean(HumanDao.class);
        EntityContainerWindow.showWindow(new EntityContainer<>(humanDao.findAll(), HumanItem.class), additionalActions);
    }
}
