package com.appspot.natanedwin.service.appsession;

import com.appspot.natanedwin.app.AppUI;
import com.appspot.natanedwin.entity.Establishment;
import com.appspot.natanedwin.entity.UserAccount;
import com.appspot.natanedwin.service.user.UserCredentials;
import com.vaadin.server.VaadinSession;

/**
 *
 * @author prokob01
 */
public interface AppSession {

    public VaadinSession getVaadinSession();

    public AppUI getAppUI();

    public void shutdown();

    public boolean isSuperAdmin();

    public UserAccount getUserAccount();

    public Establishment getEstablishment();

    public void setEstablishment(Establishment establishment);

    public void parseUserCredentials(UserCredentials uc);

}
