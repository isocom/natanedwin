package com.appspot.natanedwin.servlet.fix;

import com.appspot.natanedwin.dao.RfidEventDao;
import com.appspot.natanedwin.entity.Human;
import com.appspot.natanedwin.entity.RfidCard;
import com.appspot.natanedwin.entity.RfidEvent;
import com.appspot.natanedwin.service.spring.SpringContext;
import com.googlecode.objectify.Key;
import java.io.PrintWriter;

public class RfidEventFix {

    public static void fixCardIndexing(PrintWriter writer) {
        RfidEventDao dao = SpringContext.INSTANCE.getBean(RfidEventDao.class);
        for (RfidEvent e : dao.findAll()) {
            if (e.getHuman() != null) {
                continue;
            }
            RfidCard safe = e.getRfidCard().safe();
            Key<Human> key = safe.getHuman().getKey();
            e.setHuman(key);
            dao.save(e);
            writer.println("Zapisano: " + e + " | " + e.getRfidCard() + e + " | " + e.getHuman());
        }
    }
}
