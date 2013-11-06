/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appspot.natanedwin.servlet.fix;

import com.appspot.natanedwin.dao.RfidEventDao;
import com.appspot.natanedwin.entity.RfidEvent;
import com.appspot.natanedwin.service.spring.SpringContext;
import java.io.PrintWriter;

/**
 *
 * @author rr163240
 */
public class RfidEventFix {

    public static void fixCardIndexing(PrintWriter writer) {
        RfidEventDao dao = SpringContext.INSTANCE.getBean(RfidEventDao.class);
        for (RfidEvent e : dao.findAll()) {
            dao.save(e);
            writer.println("Zapisano: " + e + " | " + e.getRfidCard() + e + " | " + e.getRfidCard());
        }
    }
}
