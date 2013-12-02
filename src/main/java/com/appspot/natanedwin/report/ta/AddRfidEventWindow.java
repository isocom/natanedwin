package com.appspot.natanedwin.report.ta;

import com.appspot.natanedwin.dao.EstablishmentDao;
import com.appspot.natanedwin.entity.Establishment;
import com.appspot.natanedwin.entity.Human;
import com.appspot.natanedwin.entity.RfidEvent;
import com.appspot.natanedwin.entity.RfidEventType;
import com.appspot.natanedwin.service.appsession.AppSession;
import com.appspot.natanedwin.service.appsession.AppSessionHelper;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.InlineDateField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.joda.time.DateTimeZone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

/**
 *
 * @author prokob01
 */
@Configurable(preConstruction = true)
public class AddRfidEventWindow extends Window {
    
    @Autowired
    private transient AppSession appSession;
    @Autowired
    private transient EstablishmentDao establishmentDao;
    
    public static void show() {
        UI current = UI.getCurrent();
        current.addWindow(new AddRfidEventWindow());
    }
    
    public AddRfidEventWindow() {
        setCaption("Dodaj zdarzenie");
        VerticalLayout verticalLayout = new VerticalLayout();
        
        final ComboBox comboBoxType = new ComboBox("Typ zdarzenia");
        comboBoxType.setNullSelectionAllowed(false);
        comboBoxType.addItem(RfidEventType.In);
        comboBoxType.addItem(RfidEventType.TempOut);
        comboBoxType.addItem(RfidEventType.TempIn);
        comboBoxType.addItem(RfidEventType.Out);
        verticalLayout.addComponent(comboBoxType);
        
        DateTimeZone dateTimeZone = AppSessionHelper.dateTimeZone(appSession);
        Locale locale = AppSessionHelper.locale(appSession);
        
        final InlineDateField inlineDataField = new InlineDateField();
        inlineDataField.setImmediate(true);
        inlineDataField.setTimeZone(dateTimeZone.toTimeZone());
        inlineDataField.setLocale(locale);
        inlineDataField.setResolution(Resolution.MINUTE);
        inlineDataField.setValue(new Date());
        verticalLayout.addComponent(inlineDataField);
        
        long establishmentId = AppSessionHelper.establishmentId(appSession);
        Establishment establishment = establishmentDao.byId(establishmentId);
        List<Human> humans = establishment.safeHumans();
        final ComboBox comboBoxHuman = new ComboBox("Osoba", humans);
        comboBoxHuman.setNullSelectionAllowed(false);
        verticalLayout.addComponent(comboBoxHuman);
        
        verticalLayout.addComponent(new Button("Zarejestruj", new Button.ClickListener() {
            
            @Override
            public void buttonClick(ClickEvent event) {
                RfidEvent rfidEvent = new RfidEvent();
                rfidEvent.setEventDate(inlineDataField.getValue());
                rfidEvent.setHuman((Human) comboBoxHuman.getValue());
                rfidEvent.setRfidEventType((RfidEventType) comboBoxType.getValue());
            }
        }));
        
        verticalLayout.addComponent(new Button("Zamknij", new Button.ClickListener() {
            
            @Override
            public void buttonClick(ClickEvent event) {
                close();
            }
        }));
        setContent(verticalLayout);
        center();
    }
}
