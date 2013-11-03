package com.appspot.natanedwin.service.appsession;

import com.appspot.natanedwin.app.AppUI;
import com.appspot.natanedwin.service.user.UserCredentials;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.UI;
import org.springframework.stereotype.Service;

/**
 *
 * @author prokob01
 */
@Service
public class AppSessionImpl implements AppSession {

    @Override
    public VaadinSession getVaadinSession() {
        return VaadinSession.getCurrent();
    }

    @Override
    public AppUI getAppUI() {
        return (AppUI) UI.getCurrent();
    }

    @Override
    public UserCredentials getUserCredentials() {
        VaadinSession current = VaadinSession.getCurrent();
        return current.getAttribute(UserCredentials.class);
    }

    @Override
    public void setUserCredentials(UserCredentials uc) {
        VaadinSession current = VaadinSession.getCurrent();
        current.setAttribute(UserCredentials.class, uc);
    }

    @Override
    public void shitdown() {
        UI.getCurrent().getPage().setLocation("/");
        VaadinSession.getCurrent().close();
    }
}
