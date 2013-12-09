package com.appspot.natanedwin.app.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

public class TimeAttendanceView extends VerticalLayout implements View {

    public TimeAttendanceView() {
        setSizeFull();

        final Label title = new Label("Rejestracja Czasu Pracy (RCP)");
        title.setSizeUndefined();
        title.addStyleName("h1");
        addComponent(title);

        TabSheet tabSheet = new TabSheet();
        tabSheet.setSizeFull();
        addComponent(tabSheet);
        tabSheet.addTab(new TimeAttendanceView1(), "Stan dnia");
        tabSheet.addTab(new TimeAttendanceView2(), "Raport miesięczny");
        tabSheet.addTab(tab3(), "Raport Przyszły 1");
        setExpandRatio(tabSheet, 1);
    }

    private Component tab3() {
        VerticalLayout verticalLayout = new VerticalLayout();
        return verticalLayout;
    }

    @Override
    public void enter(ViewChangeEvent event) {
    }
}
