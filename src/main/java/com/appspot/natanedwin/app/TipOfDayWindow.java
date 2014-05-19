package com.appspot.natanedwin.app;

import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.apache.commons.io.IOUtils;

public class TipOfDayWindow extends Window {

    public static void show() {
        UI current = UI.getCurrent();
        current.addWindow(new TipOfDayWindow());
    }

    public TipOfDayWindow() {
        super("Porada dnia");
        setCaption("Informacja o programie...");
        VerticalLayout verticalLayout = new VerticalLayout();
        Panel panel = new Panel("Czy wiesz że...", new Label(readTipOfDay(), ContentMode.HTML));
        panel.setWidth(450, Unit.PIXELS);
        verticalLayout.addComponent(panel);
        verticalLayout.addComponent(new Button("Zamknij", new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                close();
            }
        }));
        setContent(verticalLayout);
        
        center();
    }

    private String readTipOfDay() {
        try {
            InputStream inputStream = TipOfDayWindow.class.getResourceAsStream("tipofday/TipOfDay001.html");
//            InputStream inputStream = TipOfDayWindow.class.getResourceAsStream("/com/appspot/natanedwin/app/tipofday/TipOfDay001.txt");
            List<String> readLines = IOUtils.readLines(inputStream, "UTF-8");
            StringBuilder sb = new StringBuilder();
            for (String s : readLines) {
                sb.append(s).append('\n');
            }
            return sb.toString();
        } catch (IOException ioe) {
            return ioe.getMessage();
        }
    }

}
