package com.appspot.natanedwin.app.menu.ta;

import com.appspot.natanedwin.report.ta.AddRfidEventWindow;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;

/**
 *
 * @author prokob01
 */
public class AddRfidEvent implements MenuBar.Command {

    static final long serialVersionUID = 2117727807339680518L;

    @Override
    public void menuSelected(MenuItem selectedItem) {
        AddRfidEventWindow.show();
    }
}
