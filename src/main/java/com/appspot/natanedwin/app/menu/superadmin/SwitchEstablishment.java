package com.appspot.natanedwin.app.menu.superadmin;

import com.appspot.natanedwin.app.AppNavigator;
import com.appspot.natanedwin.dao.EstablishmentDao;
import com.appspot.natanedwin.dao.RfidCardDao;
import com.appspot.natanedwin.entity.Establishment;
import com.appspot.natanedwin.service.appsession.AppSession;
import com.appspot.natanedwin.vaadin.EntityAction;
import com.appspot.natanedwin.vaadin.EntityContainer;
import com.appspot.natanedwin.vaadin.EntityItemSelectWindow;
import com.appspot.natanedwin.vaadin.entity.EstablishmentItem;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

/**
 *
 * @author prokob01
 */
@Configurable
public class SwitchEstablishment implements MenuBar.Command {
    
    @Autowired
    private transient AppSession appSession;
    @Autowired
    private transient EstablishmentDao establishmentDao;
    
    @Override
    public void menuSelected(MenuItem selectedItem) {
        List<Establishment> findAll = establishmentDao.findAll();
        EntityContainer<Establishment> entityContainer = new EntityContainer<>(findAll, EstablishmentItem.class);
        EntityItemSelectWindow.showWindow(entityContainer, new EntityAction<Establishment>() {
            
            @Override
            public void execute(Establishment entity) {
                appSession.setEstablishment(entity);
                appSession.getAppUI().getAppNavigator().navigateTo(AppNavigator.ViewDestination.HOME);
            }
        });
    }
}
