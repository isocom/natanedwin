/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appspot.natanedwin.service.user;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author prokob01
 */
public interface UserManager {

    public UserCredentials discoverCredentials(HttpServletRequest request);
    public String hashPassword(String password);
    public String generatePassword();
}
