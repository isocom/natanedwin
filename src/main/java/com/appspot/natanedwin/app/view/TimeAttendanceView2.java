package com.appspot.natanedwin.app.view;

import com.appspot.natanedwin.report.Report;
import com.appspot.natanedwin.report.ta.MonthSummary;
import com.appspot.natanedwin.vaadin.component.ComboBoxMonth;
import com.appspot.natanedwin.vaadin.component.ComboBoxMonth.Month;
import com.appspot.natanedwin.vaadin.component.ComboBoxYear;
import com.vaadin.data.Container;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import java.util.Map;

public class TimeAttendanceView2 extends VerticalLayout {

    private VerticalLayout content = new VerticalLayout();

    public TimeAttendanceView2() {
        final HorizontalLayout toolbar = new HorizontalLayout();
        final ComboBoxYear comboBoxYear = new ComboBoxYear("Wybierz rok");
        final ComboBoxMonth comboBoxMonth = new ComboBoxMonth("i miesiąc");
        toolbar.addComponent(comboBoxYear);
        toolbar.addComponent(comboBoxMonth);
        toolbar.addComponent(new Button("Przelicz", new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                Integer year = (Integer) comboBoxYear.getValue();
                if (year == null) {
                    Notification.show("Najpierw wybierz rok");
                    return;
                }
                Month month = (Month) comboBoxMonth.getValue();
                if (month == null) {
                    Notification.show("Najpierw wybierz miesiąc");
                    return;
                }

                Report report = new MonthSummary(year, month.getId());
                x(report);
            }
        }));
        toolbar.addComponent(new Button("Generuj XLS"));
        toolbar.addComponent(new Button("Generuj PDF"));
        addComponent(toolbar);
        addComponent(content);
        setExpandRatio(content, 1);
        content.addComponent(new Button("12344"));
        content.addComponent(new Button("12344"));
        content.addComponent(new Button("12344"));
        content.addComponent(new Button("12344"));
        content.addComponent(new Button("12344"));
        content.addComponent(new Button("12344"));
        content.addComponent(new Button("12344"));
    }

    private void x(Report report) {
        Map<String, Container> vaadinData = report.asVaadinData();
        Accordion accordion = new Accordion();
        accordion.setSizeFull();
        for (String key : vaadinData.keySet()) {
            Table table = new Table(key, vaadinData.get(key));
            table.setSizeFull();
            accordion.addTab(table, key);
        }
        content.removeAllComponents();
        content.addComponent(accordion);
    }
}
