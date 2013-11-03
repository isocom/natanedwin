package com.appspot.natanedwin.dao;

import com.appspot.natanedwin.app.AppError;
import com.appspot.natanedwin.entity.UserAccount;
import com.appspot.natanedwin.entity.UserAccountType;
import com.appspot.natanedwin.service.ofy.Ofy;
import com.appspot.natanedwin.service.spammer.SpamType;
import com.appspot.natanedwin.service.spammer.Spammer;
import com.appspot.natanedwin.service.user.UserManager;
import com.googlecode.objectify.cmd.Query;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author prokob01
 */
@Repository
public class UserAccountDao implements Dao<UserAccount> {

    @Autowired
    private Ofy ofy;
    @Autowired
    Spammer spammer;
    @Autowired
    UserManager userManager;

    @Override
    public UserAccount byId(long id) {
        return ofy.ofy().load().type(UserAccount.class).id(id).safe();
    }

    public List<UserAccount> findAll() {
        Query<UserAccount> query = ofy.ofy().load().type(UserAccount.class);
        List<UserAccount> list = query.list();
        ofy.ofy().clear();
        return list;
    }

    public UserAccount findByUserId(String userId) {
        Query<UserAccount> query = ofy.ofy().load().type(UserAccount.class).filter("userId", userId);
        return query.first().now();
    }

    public List<UserAccount> findEstablishmentUnassigned() {
        return ofy.ofy().load().type(UserAccount.class).filter("establishment == ", null).list();
    }

    @Override
    public UserAccount delete(UserAccount entity) {
        throw new AppError("Can't delete " + entity.getClass().getSimpleName(), "Nie można usuwać tego typu obiektów");
    }

    @Override
    public UserAccount save(UserAccount e) {
        boolean sendWelcomeEmail = e.getId() == null;
        String password = null;

        if (e.getId() == null) {
            if (findByUserId(e.getUserId()) != null) {
                throw new AppError("Konto już istnieje", "Istnieje już użytkownik o identyfikatorze: " + e.getUserId());
            }
            password = userManager.generatePassword();
            e.setPasswordHash(userManager.hashPassword(password));
        }

        ofy.ofy().save().entity(e).now();
        if (sendWelcomeEmail) {
            if (e.getUserAccountType() == UserAccountType.Internal) {
                spammer.spam(SpamType.UserAccountCreated, e, password);
            }
            if (e.getUserAccountType() == UserAccountType.GoogleAccount) {
                spammer.spam(SpamType.UserGoogleAccount, e, password);
            }
        }
        return e;
    }
}
