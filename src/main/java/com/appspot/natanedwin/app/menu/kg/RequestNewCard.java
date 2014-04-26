package com.appspot.natanedwin.app.menu.kg;

import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;

/**
 *
 * @author prokob01
 */
public class RequestNewCard implements MenuBar.Command {

    @Override
    public void menuSelected(MenuItem selectedItem) {
        RequestNewCardWindow.showWindow();
    }
}
