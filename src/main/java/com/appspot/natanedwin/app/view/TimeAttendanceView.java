package com.appspot.natanedwin.app.view;

import com.appspot.natanedwin.dao.RfidEventDao;
import com.appspot.natanedwin.entity.Human;
import com.appspot.natanedwin.entity.RfidCard;
import com.appspot.natanedwin.entity.RfidEvent;
import com.appspot.natanedwin.entity.UserAccountTools;
import com.appspot.natanedwin.service.appsession.AppSession;
import com.appspot.natanedwin.service.ofy.Ofy;
import com.appspot.natanedwin.service.spring.SpringContext;
import com.googlecode.objectify.Objectify;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.VerticalLayout;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class TimeAttendanceView extends VerticalLayout implements View {
    
    public TimeAttendanceView() {
        setSizeFull();
        
        final Label title = new Label("Rejestracja czasu pracy");
        title.setSizeUndefined();
        title.addStyleName("h1");
        addComponent(title);
        
        final Label label = new Label(report(new Date()), ContentMode.HTML);
        
        final HorizontalLayout toolbar = new HorizontalLayout();
        final PopupDateField popupDateField = new PopupDateField("Podaj dzień raportu", new Date());
        popupDateField.setDateFormat("yyyy-MM-dd");
        toolbar.addComponent(popupDateField);
        toolbar.addComponent(new Button("Przelicz", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                label.setValue(report(popupDateField.getValue()));
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
    
    private String report(Date date) {
        RfidEventDao rfidEventDao = SpringContext.INSTANCE.getBean(RfidEventDao.class);
        Ofy ofy = SpringContext.INSTANCE.getBean(Ofy.class);
        AppSession appSession = SpringContext.INSTANCE.getBean(AppSession.class);
        
        DateTimeFormatter mediumDateTime = DateTimeFormat.mediumDateTime();
        mediumDateTime = mediumDateTime.withZone(DateTimeZone.forID(appSession.getUserCredentials().getUserAccount().getDateTimeZone()));
        mediumDateTime = mediumDateTime.withLocale(UserAccountTools.getLocale(appSession.getUserCredentials().getUserAccount().getLocale()));
        
        StringBuilder sb = new StringBuilder();
        sb.append("<body>");
        sb.append("<h2>Raport za dzień: ").append(mediumDateTime.print(date.getTime())).append("</h2>");
        
        List<RfidEvent> findToday = rfidEventDao.findToday(date);
        Set<Human> humans = new HashSet<Human>();
        
        sb.append("<h3>Zdarzenia na czytniku</h3>");
        sb.append("<ol>");
        for (RfidEvent rfidEvent : findToday) {
            sb.append("<li>");
            Objectify o = ofy.ofy();
            String instant = mediumDateTime.print(rfidEvent.getEventDate().getTime());
            sb.append(instant).append(", ").append(rfidEvent.getRfidEventType()).append(", ");
            RfidCard rfidCard = rfidEvent.getRfidCard();
            sb.append(rfidCard.getCardNumber());
            sb.append(", ");
            sb.append(rfidCard.getHuman().get().getName());
            sb.append("</li>");
            humans.add(rfidCard.getHuman().get());
        }
        sb.append("</ol>");
        
        sb.append("<h3>Lista obecności</h3>");
        sb.append("<ol>");
        for (Human human : humans) {
            sb.append("<li>").append(human.getName()).append("</li>");
        }
        sb.append("</ol>");
        
        sb.append("</body>");
        return sb.toString();
    }
}
