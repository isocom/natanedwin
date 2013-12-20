package com.appspot.natanedwin.app;

import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

public class DownloadArea extends HorizontalLayout {
    
    private Component component1 = null;
    private Component component2 = null;
    private Component component3 = null;
    
    public DownloadArea() {
        setSizeUndefined();
//        setHeight(25, Unit.PIXELS);
        addComponent(new Label("Pliki do pobrania: "));
    }
    
    public void add(Component c) {
        c.setCaption(".  " + c.getCaption() + "  .");
        if (component3 != null) {
            removeComponent(component3);
        }
        component3 = component2;
        component2 = component1;
        component1 = c;        
        addComponent(component1);
    }
}
