package com.appspot.natanedwin.vaadin.component;

import com.vaadin.ui.ComboBox;
import org.joda.time.DateTimeConstants;

public class ComboBoxMonth extends ComboBox {

    public ComboBoxMonth(String caption) {
        super(caption);
        setInputPrompt("miesiąc...");
        setPageLength(0);
        addItem(Month.JANUARY);
        addItem(Month.FEBRUARY);
        addItem(Month.MARCH);
        addItem(Month.APRIL);
        addItem(Month.MAY);
        addItem(Month.JUNE);
        addItem(Month.JULY);
        addItem(Month.AUGUST);
        addItem(Month.SEPTEMBER);
        addItem(Month.OCTOBER);
        addItem(Month.NOVEMBER);
        addItem(Month.DECEMBER);
        setNullSelectionAllowed(false);
    }

    public enum Month {

        JANUARY(DateTimeConstants.JANUARY, "Styczeń"),
        FEBRUARY(DateTimeConstants.FEBRUARY, "Luty"),
        MARCH(DateTimeConstants.MARCH, "Marzec"),
        APRIL(DateTimeConstants.APRIL, "Kwiecień"),
        MAY(DateTimeConstants.MAY, "Maj"),
        JUNE(DateTimeConstants.JUNE, "Czerwiec"),
        JULY(DateTimeConstants.JULY, "Lipiec"),
        AUGUST(DateTimeConstants.AUGUST, "Sierpień"),
        SEPTEMBER(DateTimeConstants.SEPTEMBER, "Wrzesień"),
        OCTOBER(DateTimeConstants.OCTOBER, "Październik"),
        NOVEMBER(DateTimeConstants.NOVEMBER, "Listopad"),
        DECEMBER(DateTimeConstants.DECEMBER, "Grudzień");

        private final int id;
        private final String name;

        private Month(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        @Override
        public String toString() {
            return name;
        }
    }

}
