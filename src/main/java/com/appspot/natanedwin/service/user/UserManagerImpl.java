/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appspot.natanedwin.service.user;

import com.appspot.natanedwin.dao.UserAccountDao;
import com.appspot.natanedwin.entity.UserAccount;
import com.appspot.natanedwin.entity.UserAccountType;
import com.appspot.natanedwin.service.spammer.SpamType;
import com.appspot.natanedwin.service.spammer.Spammer;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import java.security.MessageDigest;
import java.security.Principal;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author prokob01
 */
@Service
public class UserManagerImpl implements UserManager {

    @Autowired
    private UserAccountDao userAccountDao;
    @Autowired
    private Spammer spammer;

    @Override
    public UserCredentials discoverCredentials(HttpServletRequest request) {
        UserCredentials userCredentials = new UserCredentials();
        UserService userService = UserServiceFactory.getUserService();
        String thisURL = request.getRequestURI();

        if (request.getParameter("login") != null && request.getParameter("password") != null) {
            String login = request.getParameter("login").trim();
            String password = request.getParameter("password").trim();
            userCredentials.setUser(null);
            userCredentials.setUserAdmin(false);
            userCredentials.setUserAccount(loggedWithInternal(login, password));
            return userCredentials;
        }

        Principal principal = request.getUserPrincipal();
        if (principal != null) {
            userCredentials.setPrincipalName(principal.getName());
            userCredentials.setGoogleAccountLogout(userService.createLogoutURL(thisURL));
        } else {
            userCredentials.setGoogleAccountLogin(userService.createLoginURL(thisURL));
        }

        // we have an user logged in with GAE
        if (userService.isUserLoggedIn()) {
            userCredentials.setUser(userService.getCurrentUser());
            userCredentials.setUserAdmin(userService.isUserAdmin());
            userCredentials.setUserAccount(loggedWithGoogle(userCredentials.getUser()));
            return userCredentials;
        }

        userCredentials.setGoogleAccountLogin(userService.createLoginURL(thisURL));
        return userCredentials;
    }

    private UserAccount loggedWithInternal(String login, String password) {
        UserAccount userAccount = userAccountDao.findByUserId(login);
        if (userAccount == null) {
            return null;
        }
        if (hashPassword(password).equals(userAccount.getPasswordHash())) {
            return userAccount;
        } else {
            return null;
        }
    }

    private UserAccount loggedWithGoogle(User user) {
        UserAccount userAccount = userAccountDao.findByUserId(user.getUserId());
        if (userAccount != null) {
            // ensure we have latest primary user e-mail in database
            if (!user.getEmail().equals(userAccount.getEmail().getEmail())) {
                userAccount.setEmail(user.getEmail());
                userAccountDao.save(userAccount);
            }
            return userAccount;
        }

        userAccount = new UserAccount();
        userAccount.setUserAccountType(UserAccountType.GoogleAccount);
        userAccount.setUserId(user.getUserId());
        userAccount.setEmail(user.getEmail());
        userAccountDao.save(userAccount);
        return userAccount;
    }

    @Override
    public String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(password.getBytes());
            return Hex.encodeHexString(digest);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String generatePassword() {
        return RandomStringUtils.random(16, "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz");
    }
}
