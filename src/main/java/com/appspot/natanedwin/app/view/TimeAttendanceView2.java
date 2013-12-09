package com.appspot.natanedwin.app.view;

import com.appspot.natanedwin.report.Report;
import com.appspot.natanedwin.report.ta.AddRfidEventWindow;
import com.appspot.natanedwin.report.ta.MonthSummary;
import com.appspot.natanedwin.vaadin.component.ComboBoxMonth;
import com.appspot.natanedwin.vaadin.component.ComboBoxMonth.Month;
import com.appspot.natanedwin.vaadin.component.ComboBoxYear;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;

public class TimeAttendanceView2 extends VerticalLayout {

    private Report report = null;

    public TimeAttendanceView2() {
        final HorizontalLayout toolbar = new HorizontalLayout();
        final Label label = new Label("Określ najpierw miesiąc raportu", ContentMode.HTML);
        addComponent(toolbar);
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

                report = new MonthSummary(year, month.getId());
                label.setValue(report.asHTML());
            }
        }));
        toolbar.addComponent(new Button("Generuj XLS"));
        toolbar.addComponent(new Button("Generuj PDF"));
        addComponent(label);
    }

}
