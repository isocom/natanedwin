package com.appspot.natanedwin.app.view;

import com.appspot.natanedwin.dao.EstablishmentDao;
import com.appspot.natanedwin.dao.UserAccountDao;
import com.appspot.natanedwin.entity.Establishment;
import com.appspot.natanedwin.entity.UserAccount;
import com.appspot.natanedwin.service.appsession.AppSession;
import com.appspot.natanedwin.service.appsession.AppSessionHelper;
import com.appspot.natanedwin.service.user.UserCredentials;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;

@Configurable(preConstruction = true)
public class HomeView extends VerticalLayout implements View {

    @Value("${natanedwin.version}")
    private String applicationVersion;
    @Value("${natanedwin.appname}")
    private String applicationName;
    @Autowired
    private transient AppSession appSession;
    @Autowired
    private transient UserAccountDao userAccountDao;
    @Autowired
    private transient EstablishmentDao establishmentDao;

    public HomeView() {
        long userAccountId = AppSessionHelper.userAccountId(appSession);
        UserAccount userAccount = userAccountDao.byId(userAccountId);
        long establishmentId = AppSessionHelper.establishmentId(appSession);
        Establishment establishment = establishmentDao.byId(establishmentId);

        setSizeFull();
//        setCaption("Informacja o systemie:");
        Label title = new Label("Witaj w naszej aplikacji");
        title.addStyleName("h1");
        addComponent(title);
        addComponent(new Label("Bieżąca wersja: " + applicationName + " / " + applicationVersion));
        String google = (appSession.getUserCredentials().getPrincipalName() != null) ? (" -> " + appSession.getUserCredentials().getPrincipalName()) : "";
        addComponent(new Label("Typ użytkownika: " + userAccount.getUserAccountType() + google));
        addComponent(new Label("Id użytkownika: " + userAccount.getId() + " / " + userAccount.getUserId()));
        addComponent(new Label("Organizacja: " + establishment.getName()));
        addComponent(new Label("Ważność licencji: " + establishment.getLicenseValidity()));
    }

    @Override
    public void enter(ViewChangeEvent event) {
    }

}
