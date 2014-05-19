package com.appspot.natanedwin.app;

import com.appspot.natanedwin.service.appsession.AppSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

@Configurable
public class SideBar extends VerticalLayout {

    static final long serialVersionUID = -8245696392571705757L;
    @Autowired
    private transient AppSession appSession;

    public SideBar() {
        setSizeUndefined();
        addComponent(new Button(" . ", new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                appSession.getAppUI().getAppNavigator().navigateTo(AppNavigator.ViewDestination.HOME);
            }
        }));
        addComponent(new Button(" | ", new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                TipOfDayWindow.show();
            }
        }));
    }

}
