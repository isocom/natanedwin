package com.appspot.natanedwin.app.menu.superadmin;

import com.appspot.natanedwin.entity.Device;
import com.appspot.natanedwin.service.ofy.Ofy;
import com.appspot.natanedwin.service.spring.SpringContext;
import com.appspot.natanedwin.vaadin.EntityContainerWindow;
import com.appspot.natanedwin.vaadin.EntityContainer;
import com.appspot.natanedwin.vaadin.entity.DeviceItem;
import com.googlecode.objectify.cmd.Query;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import java.util.List;

public class ShowDevices implements MenuBar.Command {

    @Override
    public void menuSelected(MenuItem selectedItem) {
        Ofy ofy = SpringContext.INSTANCE.getBean(Ofy.class);
        Query<Device> query = ofy.ofy().load().type(Device.class);
        List<Device> list = query.list();
        ofy.ofy().clear();

        EntityContainerWindow.showWindow(new EntityContainer<Device>(list, DeviceItem.class));
    }
}
