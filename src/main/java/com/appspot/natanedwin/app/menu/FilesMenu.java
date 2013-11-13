package com.appspot.natanedwin.app.menu;

import com.appspot.natanedwin.app.menu.files.ShowHumans;
import com.vaadin.ui.MenuBar;

public class FilesMenu {

    static void buildMenuFiles(MenuBar menuBar) {
        MenuBar.MenuItem menu = menuBar.addItem("Kartoteki", null);
        
        menu.addItem("Pokaż osoby", new ShowHumans());
    }

}
