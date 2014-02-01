package com.appspot.natanedwin.app.menu;

import com.appspot.natanedwin.app.menu.kg.ShowKinderGarten;
import com.vaadin.ui.MenuBar;

public class KinderGartenMenu {

    static void build(MenuBar menuBar) {
        MenuBar.MenuItem menu = menuBar.addItem("Przedszkole", null);

        menu.addItem("Pokaż Moduł Przedszkolny", new ShowKinderGarten());
        menu.addSeparator();
        menu.addItem("Zamów kartę dla NOWEGO dziecka", null);
        menu.addItem("Zamów kartę dla DODATKOWĄ", null);
    }

}
