/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appspot.natanedwin.app.menu.profile;

import com.appspot.natanedwin.dao.UserAccountDao;
import com.appspot.natanedwin.entity.UserAccount;
import com.appspot.natanedwin.entity.UserAccountType;
import com.appspot.natanedwin.service.appsession.AppSession;
import com.appspot.natanedwin.service.spring.SpringContext;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Notification;

/**
 *
 * @author rr163240
 */
public class ChangePassword implements MenuBar.Command {

    static final long serialVersionUID = 2581487018090710349L;

    @Override
    public void menuSelected(MenuBar.MenuItem selectedItem) {
        AppSession appSession = SpringContext.INSTANCE.getBean(AppSession.class);
        UserAccountDao userAccountDao = SpringContext.INSTANCE.getBean(UserAccountDao.class);
        UserAccount userAccount = appSession.getUserAccount();

        if (userAccount.getUserAccountType() == UserAccountType.GoogleAccount) {
            Notification.show("Nie można zmienić hasła", "Używasz konta Google do logowania, zmień swoje hasło na stronach Google", Notification.Type.WARNING_MESSAGE);
            return;
        }

        userAccountDao.resetPassword(userAccount);
        Notification.show("Nowe hasło zostało wysłane na adres: " + userAccount.getEmail().getEmail());
    }
}
