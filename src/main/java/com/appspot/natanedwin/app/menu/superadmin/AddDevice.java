/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appspot.natanedwin.app.menu.superadmin;

import com.appspot.natanedwin.entity.Device;
import com.appspot.natanedwin.vaadin.EntityItemWindow;
import com.appspot.natanedwin.vaadin.entity.DeviceItem;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;

/**
 *
 * @author prokob01
 */
public class AddDevice implements MenuBar.Command {

    @Override
    public void menuSelected(MenuItem selectedItem) {
        EntityItemWindow.showWindow(new DeviceItem(new Device()));
    }
}
