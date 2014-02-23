package com.appspot.natanedwin.app.menu.superadmin;

import com.appspot.natanedwin.entity.Human;
import com.appspot.natanedwin.vaadin.EntityAction;
import com.appspot.natanedwin.vaadin.EntityItemWindow;
import com.appspot.natanedwin.vaadin.entity.HumanItem;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author prokob01
 */
public class AddHuman implements MenuBar.Command {

    static final long serialVersionUID = 6198839062656772552L;
    private Map<String, EntityAction> additionalActions = new LinkedHashMap<String, EntityAction>();

    public AddHuman() {
        additionalActions.put("Przypisz kartę", new AssignCardToHuman1());
    }

    @Override
    public void menuSelected(MenuItem selectedItem) {
        EntityItemWindow.showWindow(new HumanItem(new Human()), additionalActions);
    }
}
