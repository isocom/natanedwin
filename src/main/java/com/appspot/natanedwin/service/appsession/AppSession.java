package com.appspot.natanedwin.service.appsession;

import com.appspot.natanedwin.app.AppUI;
import com.appspot.natanedwin.service.user.UserCredentials;
import com.vaadin.server.VaadinSession;

/**
 *
 * @author prokob01
 */
public interface AppSession {

    public VaadinSession getVaadinSession();
    
    public AppUI getAppUI();

    public UserCredentials getUserCredentials();

    public void setUserCredentials(UserCredentials uc);

}
