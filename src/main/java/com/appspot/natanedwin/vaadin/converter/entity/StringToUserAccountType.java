package com.appspot.natanedwin.vaadin.converter.entity;

import com.appspot.natanedwin.entity.UserAccountType;
import com.vaadin.data.util.converter.Converter;
import java.util.Locale;

/**
 *
 * @author prokob01
 */
public class StringToUserAccountType implements Converter<String, UserAccountType> {

    @Override
    public UserAccountType convertToModel(String value, Class<? extends UserAccountType> targetType, Locale locale) throws ConversionException {
        return value == null ? null : UserAccountType.valueOf(value);
    }

    @Override
    public String convertToPresentation(UserAccountType value, Class<? extends String> targetType, Locale locale) throws ConversionException {
        return value == null ? null : value.name();
    }

    @Override
    public Class<UserAccountType> getModelType() {
        return UserAccountType.class;
    }

    @Override
    public Class<String> getPresentationType() {
        return String.class;
    }
}
