/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appspot.natanedwin.service.user;

import com.appspot.natanedwin.entity.UserAccount;
import com.google.appengine.api.users.User;
import java.io.Serializable;

/**
 *
 * @author prokob01
 */
public class UserCredentials implements Serializable {

    private UserAccount userAccount = null;
    private User user = null;
    private String principalName = null;
    private String googleAccountLogin = null;
    private String googleAccountLogout = null;
    private boolean userAdmin = false;

    public boolean isLoggedIn() {
        return userAccount != null;
    }

    public boolean isGoogleCredentials() {
        return user != null;
    }

    public String getFriendlyUserName() {
        if (!isLoggedIn()) {
            throw new IllegalStateException();
        }

        if (isGoogleCredentials()) {
            return principalName;
        } else {
            return userAccount.getUserId();
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (user != null) {
            sb.append("GAE_User:").append(user.getEmail()).append(". ");
        }
        if (userAccount != null) {
            sb.append("UserAccount:").append(userAccount.getUserId()).append(". ");
        }
        return sb.toString().trim();
    }

    ////////////////////////////////////////////////////////////////////////////
    // GETTERS AND SETTERS
    ////////////////////////////////////////////////////////////////////////////
    public UserAccount getUserAccount() {
        return userAccount;
    }

    void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    public String getPrincipalName() {
        return principalName;
    }

    void setPrincipalName(String principalName) {
        this.principalName = principalName;
    }

    public String getGoogleAccountLogin() {
        return googleAccountLogin;
    }

    void setGoogleAccountLogin(String googleAccountLogin) {
        this.googleAccountLogin = googleAccountLogin;
    }

    public String getGoogleAccountLogout() {
        return googleAccountLogout;
    }

    void setGoogleAccountLogout(String googleAccountLogout) {
        this.googleAccountLogout = googleAccountLogout;
    }

    public boolean isUserAdmin() {
        return userAdmin;
    }

    void setUserAdmin(boolean userAdmin) {
        this.userAdmin = userAdmin;
    }

    public User getUser() {
        return user;
    }

    void setUser(User user) {
        this.user = user;
    }
}
