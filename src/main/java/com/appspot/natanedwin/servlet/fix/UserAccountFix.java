package com.appspot.natanedwin.servlet.fix;

import com.appspot.natanedwin.dao.UserAccountDao;
import com.appspot.natanedwin.entity.Establishment;
import com.appspot.natanedwin.entity.UserAccount;
import com.appspot.natanedwin.service.spring.SpringContext;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import java.io.PrintWriter;

/**
 *
 * @author rr163240
 */
public class UserAccountFix {

    public static void fixReSave(PrintWriter writer) {
        UserAccountDao uad = SpringContext.INSTANCE.getBean(UserAccountDao.class);
        for (UserAccount ua : uad.findAll()) {
            uad.save(ua);
            writer.println("Zapisano: " + ua + " | " + ua.getUserId());
        }
    }
    public static void fixRefEstablishment(PrintWriter writer) {
        UserAccountDao uad = SpringContext.INSTANCE.getBean(UserAccountDao.class);
        for (UserAccount ua : uad.findAll()) {
            ua.setEstablishment(Ref.create(Key.create(Establishment.class, 1293047)));
            uad.save(ua);
            writer.println("Zapisano: " + ua + " | " + ua.getUserId());
        }
    }
}
