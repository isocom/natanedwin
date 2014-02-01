package com.appspot.natanedwin.app.menu;

import com.appspot.natanedwin.app.menu.ta.AddRfidEvent;
import com.appspot.natanedwin.app.menu.ta.ShowTimeAttendance;
import com.vaadin.ui.MenuBar;

public class TimeAttendanceMenu {

    static void build(MenuBar menuBar) {
        MenuBar.MenuItem menu = menuBar.addItem("RCP", null);
        
        menu.addItem("Pokaż Moduł RCP", new ShowTimeAttendance());
        menu.addItem("Dodaj zdarzenie RFID", new AddRfidEvent());
    }

}
