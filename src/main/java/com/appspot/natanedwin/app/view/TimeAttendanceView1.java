package com.appspot.natanedwin.app.view;

import com.appspot.natanedwin.report.ByteArrayStreamResource;
import com.appspot.natanedwin.report.Report;
import com.appspot.natanedwin.report.ta.DayStatus;
import static com.appspot.natanedwin.report.ta.DayStatus.LISTA_NIEOBECNYCH;
import static com.appspot.natanedwin.report.ta.DayStatus.RAPORT_DZIENNY;
import com.appspot.natanedwin.service.appsession.AppSession;
import com.appspot.natanedwin.service.appsession.AppSessionHelper;
import com.appspot.natanedwin.service.spring.SpringContext;
import com.vaadin.server.BrowserWindowOpener;
import com.vaadin.server.StreamResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

@Configurable(preConstruction = true)
public class TimeAttendanceView1 extends VerticalLayout {

    private Report report = new DayStatus(new Date());
    @Autowired
    private transient AppSession appSession;
    private final VerticalLayout content;

    public TimeAttendanceView1() {
        final HorizontalLayout toolbar = new HorizontalLayout();
        final PopupDateField popupDateField = new PopupDateField("Podaj dzień raportu", new Date());
        popupDateField.setDateFormat("yyyy-MM-dd");
        popupDateField.setLocale(AppSessionHelper.locale(appSession));
        toolbar.addComponent(popupDateField);
        toolbar.addComponent(new Button("Przelicz", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                report = new DayStatus(popupDateField.getValue());
                updateTables();
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
        content = new VerticalLayout();
        content.setSizeUndefined();
        addComponent(content);
        setExpandRatio(content, 1);
        updateTables();
    }

    private void updateTables() {
        content.removeAllComponents();

        final Label title = new Label(report.getFileName());
        title.setSizeUndefined();
        title.addStyleName("h2");
        content.addComponent(title);

        final Table table1 = new Table(RAPORT_DZIENNY, report.asVaadinData().get(RAPORT_DZIENNY));
        table1.setSizeFull();
        table1.setSelectable(true);
        content.addComponent(table1);
//        content.setExpandRatio(table1, 1);

        HorizontalLayout buttons = new HorizontalLayout();
        buttons.addComponent(new Button("Usuń zdarzenie OD", new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                Notification.show("Będzie w następnej wersji...");
            }
        }));
        buttons.addComponent(new Button("Usuń zdarzenie DO", new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                Notification.show("Będzie w następnej wersji...");
            }
        }));
        content.addComponent(buttons);

        final Table table2 = new Table(LISTA_NIEOBECNYCH, report.asVaadinData().get(LISTA_NIEOBECNYCH));
        table2.setSizeFull();
        table2.setSelectable(true);
        content.addComponent(table2);
//        content.setExpandRatio(table2, 1);
    }
}
