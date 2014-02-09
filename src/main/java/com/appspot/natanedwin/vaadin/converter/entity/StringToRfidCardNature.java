/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appspot.natanedwin.vaadin.converter.entity;

import com.appspot.natanedwin.entity.RfidCardNature;
import com.vaadin.data.util.converter.Converter;
import java.util.Locale;

/**
 *
 * @author prokob01
 */
public class StringToRfidCardNature implements Converter<String, RfidCardNature> {

    @Override
    public RfidCardNature convertToModel(String value, Class<? extends RfidCardNature> targetType, Locale locale) throws ConversionException {
        return value == null ? null : RfidCardNature.valueOf(value);
    }

    @Override
    public String convertToPresentation(RfidCardNature value, Class<? extends String> targetType, Locale locale) throws ConversionException {
        return value == null ? null : value.name();
    }

    @Override
    public Class<RfidCardNature> getModelType() {
        return RfidCardNature.class;
    }

    @Override
    public Class<String> getPresentationType() {
        return String.class;
    }
}
