/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appspot.natanedwin.service.mailer;

/**
 *
 * @author prokob01
 */
public class EmailAddress {

    private final String fullName;
    private final String emailAddress;

    public EmailAddress(String fullName, String emailAddress) {
        this.fullName = fullName;
        this.emailAddress = emailAddress;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }
}