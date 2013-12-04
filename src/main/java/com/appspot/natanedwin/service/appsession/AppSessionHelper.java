package com.appspot.natanedwin.service.appsession;

import com.appspot.natanedwin.app.AppNavigator;
import com.appspot.natanedwin.app.AppUI;
import com.vaadin.ui.UI;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

public final class AppSessionHelper {

    private static final Map<String, Locale> cache = new HashMap<>();

    private AppSessionHelper() {
        throw new IllegalStateException();
    }

    public static AppUI getAppUI() {
        return (AppUI) UI.getCurrent();
    }

    public static AppNavigator getAppNavigator() {
        return getAppUI().getAppNavigator();
    }

    public static long userAccountId(AppSession appSession) {
        return appSession.getUserCredentials().getUserAccount().getId();
    }

    public static long establishmentId(AppSession appSession) {
        return appSession.getUserCredentials().getUserAccount().getEstablishment().getKey().getId();
    }

    public static DateTimeZone dateTimeZone(AppSession appSession) {
        return DateTimeZone.forID(appSession.getUserCredentials().getUserAccount().getDateTimeZone());
    }

    public static Locale locale(AppSession appSession) {
        return locale(appSession.getUserCredentials().getUserAccount().getLocale());
    }

    private static Locale locale(String localeString) {
        if (cache.containsKey(localeString)) {
            return cache.get(localeString);
        }

        String[] strings = localeString.split("_");
        Locale locale;
        switch (strings.length) {
            case 1:
                locale = new Locale(strings[0]);
                break;
            case 2:
                locale = new Locale(strings[0], strings[1]);
                break;
            case 3:
                locale = new Locale(strings[0], strings[1], strings[2]);
                break;
            default:
                throw new IllegalArgumentException();
        }
        cache.put(localeString, locale);
        return locale;
    }

    public static DateTimeFormatter mediumDateTime(AppSession appSession) {
        DateTimeFormatter mediumDateTime = DateTimeFormat.mediumDateTime();
        mediumDateTime = mediumDateTime.withZone(dateTimeZone(appSession));
        mediumDateTime = mediumDateTime.withLocale(locale(appSession));
        return mediumDateTime;
    }

    public static DateTimeFormatter mediumDate(AppSession appSession) {
        DateTimeFormatter mediumDate = DateTimeFormat.mediumDate();
        mediumDate = mediumDate.withZone(dateTimeZone(appSession));
        mediumDate = mediumDate.withLocale(locale(appSession));
        return mediumDate;
    }

    public static DateTimeFormatter mediumTime(AppSession appSession) {
        DateTimeFormatter mediumTime = DateTimeFormat.mediumTime();
        mediumTime = mediumTime.withZone(dateTimeZone(appSession));
        mediumTime = mediumTime.withLocale(locale(appSession));
        return mediumTime;
    }

    public static PeriodFormatter hms() {
        return new PeriodFormatterBuilder().printZeroAlways().minimumPrintedDigits(2)
                .appendHours().appendSuffix(":")
                .appendMinutes().appendSuffix(":")
                .appendSeconds().toFormatter();
    }
}
