/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appspot.natanedwin.vaadin.converter;

import com.appspot.natanedwin.vaadin.converter.bigtable.StringToPostalAddress;
import com.appspot.natanedwin.vaadin.converter.bigtable.StringToEmail;
import com.appspot.natanedwin.vaadin.converter.entity.StringToRfidCardType;
import com.appspot.natanedwin.vaadin.converter.entity.StringToUserAccountType;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.data.util.converter.DefaultConverterFactory;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author prokob01
 */
public class CustomConverterFactory extends DefaultConverterFactory {

    private List<Converter> converters = new ArrayList<>();

    public CustomConverterFactory() {
        add(new StringToEmail());
        add(new StringToPostalAddress());

        add(new StringToRfidCardType());
        add(new StringToUserAccountType());
    }

    private void add(Converter c) {
        converters.add(c);
    }

    @Override
    protected <PRESENTATION, MODEL> Converter<PRESENTATION, MODEL> findConverter(Class<PRESENTATION> presentationType, Class<MODEL> modelType) {
        for (Converter c : converters) {
            if (presentationType == c.getPresentationType() && modelType == c.getModelType()) {
                return c;
            }
        }
        return super.findConverter(presentationType, modelType);
    }
}
