/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appspot.natanedwin.service.appsession;

import com.appspot.natanedwin.entity.UserAccountTools;
import com.appspot.natanedwin.service.spring.SpringContext;
import java.util.Locale;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.PeriodFormat;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

/**
 *
 * @author rr163240
 */
public class Formatters {

    public static DateTimeFormatter getMediumDateTime() {
        AppSession appSession = SpringContext.INSTANCE.getBean(AppSession.class);
        DateTimeFormatter mediumDateTime = DateTimeFormat.mediumDateTime();
        mediumDateTime = mediumDateTime.withZone(DateTimeZone.forID(appSession.getUserCredentials().getUserAccount().getDateTimeZone()));
        mediumDateTime = mediumDateTime.withLocale(UserAccountTools.getLocale(appSession.getUserCredentials().getUserAccount().getLocale()));
        return mediumDateTime;
    }

    public static DateTimeFormatter getMediumDate() {
        AppSession appSession = SpringContext.INSTANCE.getBean(AppSession.class);
        DateTimeFormatter mediumDateTime = DateTimeFormat.mediumDate();
        mediumDateTime = mediumDateTime.withZone(DateTimeZone.forID(appSession.getUserCredentials().getUserAccount().getDateTimeZone()));
        mediumDateTime = mediumDateTime.withLocale(UserAccountTools.getLocale(appSession.getUserCredentials().getUserAccount().getLocale()));
        return mediumDateTime;
    }

    public static DateTimeFormatter getMediumTime() {
        AppSession appSession = SpringContext.INSTANCE.getBean(AppSession.class);
        DateTimeFormatter mediumDateTime = DateTimeFormat.mediumTime();
        mediumDateTime = mediumDateTime.withZone(DateTimeZone.forID(appSession.getUserCredentials().getUserAccount().getDateTimeZone()));
        mediumDateTime = mediumDateTime.withLocale(UserAccountTools.getLocale(appSession.getUserCredentials().getUserAccount().getLocale()));
        return mediumDateTime;
    }

    public static PeriodFormatter getHMS() {
        return new PeriodFormatterBuilder()
                .appendHours().appendSuffix(":")
                .appendMinutes().appendSuffix(":")
                .appendSeconds().toFormatter();
    }
}
