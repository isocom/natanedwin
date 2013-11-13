package com.appspot.natanedwin.app.menu.files;

import com.appspot.natanedwin.dao.HumanDao;
import com.appspot.natanedwin.dao.UserAccountDao;
import com.appspot.natanedwin.entity.Human;
import com.appspot.natanedwin.entity.UserAccount;
import com.appspot.natanedwin.service.appsession.AppSession;
import com.appspot.natanedwin.service.spring.SpringContext;
import com.appspot.natanedwin.vaadin.EntityContainerWindow;
import com.appspot.natanedwin.vaadin.EntityContainer;
import com.appspot.natanedwin.vaadin.entity.HumanItem;
import com.googlecode.objectify.Ref;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author prokob01
 */
public class ShowHumans implements MenuBar.Command {

    @Override
    public void menuSelected(MenuItem selectedItem) {
        AppSession appSession = SpringContext.INSTANCE.getBean(AppSession.class);
        UserAccount userAccount = appSession.getUserCredentials().getUserAccount();
        UserAccountDao userAccountDao = SpringContext.INSTANCE.getBean(UserAccountDao.class);

        userAccount = userAccountDao.byId(userAccount.getId());
        List<Human> humans = userAccount.safeEstablishment().safeHumans();

        EntityContainerWindow.showWindowEditOnly(new EntityContainer<>(humans, HumanItem.class));
    }
}
