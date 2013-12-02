package com.appspot.natanedwin.vaadin.component;

import com.vaadin.ui.ComboBox;

public class ComboBoxYear extends ComboBox {

    public ComboBoxYear(String caption) {
        super(caption);
        setInputPrompt("rok...");
        addItem(new Integer(2013));
        addItem(new Integer(2014));
        addItem(new Integer(2015));
        setNullSelectionAllowed(false);
    }

}
