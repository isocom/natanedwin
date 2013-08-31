/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appspot.natanedwin.vaadin.converter.bigtable;

import com.google.appengine.api.datastore.Email;
import com.vaadin.data.util.converter.Converter;
import java.util.Locale;

/**
 *
 * @author prokob01
 */
public class StringToEmail implements Converter<String, Email> {

    @Override
    public Email convertToModel(String value, Class<? extends Email> targetType, Locale locale) throws ConversionException {
        return value == null ? null : new Email(value);
    }

    @Override
    public String convertToPresentation(Email value, Class<? extends String> targetType, Locale locale) throws ConversionException {
        return value == null ? null : value.getEmail();
    }

    @Override
    public Class<Email> getModelType() {
        return Email.class;
    }

    @Override
    public Class<String> getPresentationType() {
        return String.class;
    }
}
