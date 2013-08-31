package com.appspot.natanedwin.servlet.fix;

import com.appspot.natanedwin.dao.RfidCardDao;
import com.appspot.natanedwin.dao.UserAccountDao;
import com.appspot.natanedwin.entity.RfidCard;
import com.appspot.natanedwin.entity.UserAccount;
import com.appspot.natanedwin.service.cardnumber.CardNumber;
import com.appspot.natanedwin.service.spring.SpringContext;
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
}
