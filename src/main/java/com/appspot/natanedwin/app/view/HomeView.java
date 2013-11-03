package com.appspot.natanedwin.app.view;

import com.appspot.natanedwin.dao.UserAccountDao;
import com.appspot.natanedwin.entity.Establishment;
import com.appspot.natanedwin.entity.UserAccount;
import com.appspot.natanedwin.service.appsession.AppSession;
import com.appspot.natanedwin.service.spring.SpringContext;
import com.appspot.natanedwin.service.spring.SpringInformation;
import com.appspot.natanedwin.service.user.UserCredentials;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class HomeView extends VerticalLayout implements View {

    public HomeView() {
        setSizeFull();

        VerticalLayout top = new VerticalLayout();
        setupTop(top);
        addComponent(top);
    }

    @Override
    public void enter(ViewChangeEvent event) {
    }

    private void setupTop(final VerticalLayout top) {
        top.setCaption("Informacja o systemie:");

        SpringInformation springInformation = SpringContext.INSTANCE.getBean(SpringInformation.class);
        String applicationName = springInformation.getApplicationName();
        String applicationVersion = springInformation.getVersion();

        AppSession appSession = SpringContext.INSTANCE.getBean(AppSession.class);
        UserCredentials userCredentials = appSession.getUserCredentials();
        String userName = userCredentials.getPrincipalName();
        if (userName == null) {
            userName = userCredentials.getUserAccount().getUserId();
        }

        UserAccountDao userAccountDao = SpringContext.INSTANCE.getBean(UserAccountDao.class);
        Long userAccountId = appSession.getUserCredentials().getUserAccount().getId();
        UserAccount userAccount = userAccountDao.byId(userAccountId);
        Establishment establishment = userAccount.getEstablishment().safe();

        top.setWidth("100%");
        top.setSpacing(true);

        final Label title = new Label("Witaj w naszej aplikacji");
        title.addStyleName("h1");
        top.addComponent(title);
        top.addComponent(new Label("Bieżąca wersja: " + applicationVersion));
        top.addComponent(new Label("Zalogowany użytkownik: " + userName));
        top.addComponent(new Label("Organizacja: " + establishment.getName()));
        top.addComponent(new Label("Ważność licencji: " + establishment.getLicenseValidity()));
    }
}
