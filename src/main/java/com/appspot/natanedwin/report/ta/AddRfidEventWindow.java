package com.appspot.natanedwin.report.ta;

import com.appspot.natanedwin.entity.RfidEventType;
import com.appspot.natanedwin.service.appsession.AppSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
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

    public static void show() {
        UI current = UI.getCurrent();
        current.addWindow(new AddRfidEventWindow());
    }

    public AddRfidEventWindow() {
        setCaption("Dodaj zdarzenie");
        VerticalLayout verticalLayout = new VerticalLayout();

        ComboBox comboBoxType = new ComboBox("Typ zdarzenia");
        comboBoxType.setNullSelectionAllowed(false);
        comboBoxType.addItem(RfidEventType.In);
        comboBoxType.addItem(RfidEventType.TempOut);
        comboBoxType.addItem(RfidEventType.TempIn);
        comboBoxType.addItem(RfidEventType.Out);

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
