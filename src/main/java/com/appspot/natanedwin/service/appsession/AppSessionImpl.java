package com.appspot.natanedwin.service.appsession;

import com.appspot.natanedwin.app.AppUI;
import com.appspot.natanedwin.dao.EstablishmentDao;
import com.appspot.natanedwin.dao.UserAccountDao;
import com.appspot.natanedwin.entity.Establishment;
import com.appspot.natanedwin.entity.UserAccount;
import com.appspot.natanedwin.service.user.UserCredentials;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.UI;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author prokob01
 */
@Service
public class AppSessionImpl implements AppSession {

    @Autowired
    private EstablishmentDao establishmentDao;
    @Autowired
    private UserAccountDao userAccountDao;
    private final Set<Long> adminSet = new HashSet<>();

    @Override
    public VaadinSession getVaadinSession() {
        return VaadinSession.getCurrent();
    }

    @Override
    public AppUI getAppUI() {
        return (AppUI) UI.getCurrent();
    }

    @Override
    public void shutdown() {
        VaadinSession.getCurrent().close();
        VaadinService.getCurrentRequest().getWrappedSession().invalidate();
        UI.getCurrent().getPage().setLocation("/");
    }

    @Override
    public boolean isSuperAdmin() {
        return adminSet.contains(getUserAccount().getId());
    }

    @Override
    public UserAccount getUserAccount() {
        VaadinSession current = VaadinSession.getCurrent();
        return current.getAttribute(UserAccount.class);
    }

    @Override
    public Establishment getEstablishment() {
        VaadinSession current = VaadinSession.getCurrent();
        return current.getAttribute(Establishment.class);
    }

    @Override
    public void setEstablishment(Establishment establishment) {
        VaadinSession current = VaadinSession.getCurrent();
        current.setAttribute(Establishment.class, establishment);
    }

    @Override
    public void parseUserCredentials(UserCredentials uc) {
        UserAccount userAccount = uc.getUserAccount();
        userAccount = userAccountDao.byId(userAccount.getId());
        Establishment establishment = establishmentDao.byRef(userAccount.getEstablishment());
        if (uc.isUserAdmin()) {
            adminSet.add(userAccount.getId());
        }

        VaadinSession current = VaadinSession.getCurrent();
        current.setAttribute(Establishment.class, establishment);
        current.setAttribute(UserAccount.class, userAccount);
    }

}
