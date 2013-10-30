package com.appspot.natanedwin.app;

import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;

public class DownloadArea extends VerticalLayout {

    private Component component1 = null;
    private Component component2 = null;
    private Component component3 = null;

    public DownloadArea() {
        setSizeFull();
        setSpacing(false);
    }

    public void add(Component c) {
        if (component3 != null) {
            removeComponent(component3);
        }
        component3 = component2;
        component2 = component1;
        component1 = c;
        addComponent(component1);
    }
}
