package com.appspot.natanedwin.service.user;

import com.appspot.natanedwin.entity.UserAccount;
import com.google.appengine.api.users.User;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

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
        return ReflectionToStringBuilder.toString(this);
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
