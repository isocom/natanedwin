package com.appspot.natanedwin.app.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class KinderGartenView extends VerticalLayout implements View {

    public KinderGartenView() {
        setSizeFull();

        HorizontalLayout top = new HorizontalLayout();
        top.setWidth("100%");
        top.setSpacing(true);
        addComponent(top);
        final Label title = new Label("Brak licencji dla tego modułu");
        title.setSizeUndefined();
        title.addStyleName("h1");
        top.addComponent(title);
        top.setComponentAlignment(title, Alignment.MIDDLE_LEFT);
        top.setExpandRatio(title, 1);
    }

    @Override
    public void enter(ViewChangeEvent event) {
    }
}
