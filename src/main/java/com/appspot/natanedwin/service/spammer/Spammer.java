/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appspot.natanedwin.service.spammer;

/**
 *
 * @author prokob01
 */
public interface Spammer {

    public void spam(SpamType spamType, Object... args);
}
