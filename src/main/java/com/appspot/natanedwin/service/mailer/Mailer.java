/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appspot.natanedwin.service.mailer;

/**
 *
 * @author prokob01
 */
public interface Mailer {

    public void send(Email mail);

    public void sendToAdmins(String subject, String body);
}
