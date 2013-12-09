package com.appspot.natanedwin.app.menu;

import com.appspot.natanedwin.app.menu.ac.ShowAccessControl;
import com.vaadin.ui.MenuBar;

public class AcMenu {

    static void build(MenuBar menuBar) {
        MenuBar.MenuItem menu = menuBar.addItem("KD", null);
        
        menu.addItem("Pokaż Moduł Kontoli Dostępu", new ShowAccessControl());
    }

}
