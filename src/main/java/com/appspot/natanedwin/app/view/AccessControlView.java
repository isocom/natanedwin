package com.appspot.natanedwin.app.view;

import com.appspot.natanedwin.report.ByteArrayStreamResource;
import com.appspot.natanedwin.report.ta.DayStatus;
import com.appspot.natanedwin.report.Report;
import com.appspot.natanedwin.service.appsession.AppSession;
import com.appspot.natanedwin.service.spring.SpringContext;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.BrowserWindowOpener;
import com.vaadin.server.StreamResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.VerticalLayout;
import java.util.Date;

public class AccessControlView extends VerticalLayout implements View {

    private Report report;

    public AccessControlView() {
        setSizeFull();

        final Label title = new Label("Kontrola dostępu");
        title.setSizeUndefined();
        title.addStyleName("h1");
        addComponent(title);

        report = new DayStatus(new Date());

        final Label label = new Label(report.asHTML(), ContentMode.HTML);

        final HorizontalLayout toolbar = new HorizontalLayout();
        final PopupDateField popupDateField = new PopupDateField("Podaj dzień raportu", new Date());
        popupDateField.setDateFormat("yyyy-MM-dd");
        toolbar.addComponent(popupDateField);
        toolbar.addComponent(new Button("Przelicz", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                Date value = popupDateField.getValue();
                if (value == null) {
                    Notification.show("Ustaw najpierw date !!!");
                    return;
                }
                report = new DayStatus(value);
                label.setValue(report.asHTML());
            }
        }));
        toolbar.addComponent(new Button("Generuj Excel", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                ByteArrayStreamResource xls = report.asXLS();
                StreamResource resource = new StreamResource(xls, report.getFileName() + ".xls");
                BrowserWindowOpener opener = new BrowserWindowOpener(resource);
                Link link = new Link();
                link.setCaption(report.getFileName() + ".xls");
                opener.extend(link);

                AppSession appSession = SpringContext.INSTANCE.getBean(AppSession.class);
                appSession.getAppUI().getDownloadArea().add(link);
            }
        }));
        toolbar.addComponent(new Button("Generuj PDF", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                ByteArrayStreamResource pdf = report.asPDF();
                StreamResource resource = new StreamResource(pdf, report.getFileName() + ".pdf");
                BrowserWindowOpener opener = new BrowserWindowOpener(resource);
                Link link = new Link();
                link.setCaption(report.getFileName() + ".pdf");
                opener.extend(link);

                AppSession appSession = SpringContext.INSTANCE.getBean(AppSession.class);
                appSession.getAppUI().getDownloadArea().add(link);
            }
        }));
        addComponent(toolbar);

        label.setSizeFull();
        addComponent(label);
        setExpandRatio(label, 1);
    }

    @Override
    public void enter(ViewChangeEvent event) {
    }
}
