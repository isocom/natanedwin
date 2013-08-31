/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appspot.natanedwin.app;

import com.appspot.natanedwin.service.appsession.AppSession;
import com.appspot.natanedwin.service.spring.SpringContext;
import com.appspot.natanedwin.service.spring.SpringInformation;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 *
 * @author prokob01
 */
public class AppAboutWindow extends Window implements Button.ClickListener {

    public static void show() {
        UI current = UI.getCurrent();
        current.addWindow(new AppAboutWindow());
    }

    public AppAboutWindow() {
        SpringInformation springInformation = SpringContext.INSTANCE.getBean(SpringInformation.class);
        AppSession appSession = SpringContext.INSTANCE.getBean(AppSession.class);
        setCaption("Informacja o programie...");
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.addComponent(new Label(springInformation.getApplicationName()));
        verticalLayout.addComponent(new Label("Zalogowany user: " + appSession.getUserCredentials()));
        verticalLayout.addComponent(new Label("Wersja aplikacji: " + springInformation.getVersion()));
        verticalLayout.addComponent(new Button("Zamknij", this));
        setContent(verticalLayout);
        center();
    }

    @Override
    public void buttonClick(ClickEvent event) {
        UI current = UI.getCurrent();
        current.removeWindow(this);
    }
}
