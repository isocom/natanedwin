package com.appspot.natanedwin.vaadin.converter.bigtable;

import com.google.appengine.api.datastore.PostalAddress;
import com.vaadin.data.util.converter.Converter;
import java.util.Locale;

/**
 *
 * @author prokob01
 */
public class StringToPostalAddress implements Converter<String, PostalAddress> {

    @Override
    public PostalAddress convertToModel(String value, Class<? extends PostalAddress> targetType, Locale locale) throws ConversionException {
        return value == null ? null : new PostalAddress(value);
    }

    @Override
    public String convertToPresentation(PostalAddress value, Class<? extends String> targetType, Locale locale) throws ConversionException {
        return value == null ? null : value.getAddress();
    }

    @Override
    public Class<PostalAddress> getModelType() {
        return PostalAddress.class;
    }

    @Override
    public Class<String> getPresentationType() {
        return String.class;
    }
}
