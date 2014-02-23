package com.appspot.natanedwin.app.menu.files;

import com.appspot.natanedwin.dao.EstablishmentDao;
import com.appspot.natanedwin.entity.Establishment;
import com.appspot.natanedwin.entity.Human;
import com.appspot.natanedwin.service.appsession.AppSession;
import com.appspot.natanedwin.vaadin.EntityContainerWindow;
import com.appspot.natanedwin.vaadin.EntityContainer;
import com.appspot.natanedwin.vaadin.entity.HumanItem;
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
public class ShowHumans implements MenuBar.Command {

    static final long serialVersionUID = -2411280886012618462L;
    @Autowired
    private transient AppSession appSession;
    @Autowired
    private transient EstablishmentDao establishmentDao;

    @Override
    public void menuSelected(MenuItem selectedItem) {
        Establishment establishment = appSession.getEstablishment();
        establishment = establishmentDao.byId(establishment.getId());
        List<Human> humans = establishment.safeHumans();
        EntityContainerWindow.showWindowEditOnly(new EntityContainer<>(humans, HumanItem.class));
    }
}
