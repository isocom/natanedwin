/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appspot.natanedwin.vaadin.converter.entity;

import com.appspot.natanedwin.entity.RfidCardType;
import com.vaadin.data.util.converter.Converter;
import java.util.Locale;

/**
 *
 * @author prokob01
 */
public class StringToRfidCardType implements Converter<String, RfidCardType> {

    @Override
    public RfidCardType convertToModel(String value, Class<? extends RfidCardType> targetType, Locale locale) throws ConversionException {
        return value == null ? null : RfidCardType.valueOf(value);
    }

    @Override
    public String convertToPresentation(RfidCardType value, Class<? extends String> targetType, Locale locale) throws ConversionException {
        return value == null ? null : value.name();
    }

    @Override
    public Class<RfidCardType> getModelType() {
        return RfidCardType.class;
    }

    @Override
    public Class<String> getPresentationType() {
        return String.class;
    }
}
