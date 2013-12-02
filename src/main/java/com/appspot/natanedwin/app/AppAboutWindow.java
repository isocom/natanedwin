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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;

/**
 *
 * @author prokob01
 */
@Configurable(preConstruction = true)
public class AppAboutWindow extends Window implements Button.ClickListener {

    @Value("${natanedwin.version}")
    private String applicationVersion;
    @Value("${natanedwin.appname}")
    private String applicationName;
    @Autowired
    private transient AppSession appSession;

    public static void show() {
        UI current = UI.getCurrent();
        current.addWindow(new AppAboutWindow());
    }

    public AppAboutWindow() {
        setCaption("Informacja o programie...");
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.addComponent(new Label(applicationName));
        verticalLayout.addComponent(new Label("Zalogowany user: " + appSession.getUserCredentials()));
        verticalLayout.addComponent(new Label("Wersja aplikacji: " + applicationVersion));
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
