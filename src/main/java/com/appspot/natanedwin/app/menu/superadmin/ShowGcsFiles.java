package com.appspot.natanedwin.app.menu.superadmin;

import com.appspot.natanedwin.dao.GcsFileDao;
import com.appspot.natanedwin.service.spring.SpringContext;
import com.appspot.natanedwin.vaadin.EntityContainerWindow;
import com.appspot.natanedwin.vaadin.EntityContainer;
import com.appspot.natanedwin.vaadin.entity.GcsFileItem1;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;

/**
 *
 * @author prokob01
 */
public class ShowGcsFiles implements MenuBar.Command {

static final long serialVersionUID = 1288871531635171986L;
    @Override
    public void menuSelected(MenuItem selectedItem) {
        GcsFileDao gcsFileDao = SpringContext.INSTANCE.getBean(GcsFileDao.class);
        EntityContainerWindow.showWindow(new EntityContainer<>(gcsFileDao.findAll(), GcsFileItem1.class));
    }
}
