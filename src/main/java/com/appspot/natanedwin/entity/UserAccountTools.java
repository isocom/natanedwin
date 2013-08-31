/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appspot.natanedwin.entity;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 *
 * @author rr163240
 */
public class UserAccountTools {

    private static Map<String, Locale> cache = new HashMap<String, Locale>();

    public static Locale getLocale(String localeString) {
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
}
