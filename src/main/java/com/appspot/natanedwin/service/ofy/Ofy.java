/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appspot.natanedwin.service.ofy;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;

/**
 *
 * @author prokob01
 */
public interface Ofy {

    public Objectify ofy();

    public ObjectifyFactory factory();

    public boolean exists(Class<?> type, long id);
}
