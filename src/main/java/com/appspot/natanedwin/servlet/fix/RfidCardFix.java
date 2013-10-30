/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appspot.natanedwin.servlet.fix;

import com.appspot.natanedwin.dao.RfidCardDao;
import com.appspot.natanedwin.entity.RfidCard;
import com.appspot.natanedwin.service.cardnumber.CardNumber;
import com.appspot.natanedwin.service.spring.SpringContext;
import java.io.PrintWriter;

/**
 *
 * @author rr163240
 */
public class RfidCardFix {

    public static void fixAssignCardNoNumber(PrintWriter writer) {
        RfidCardDao rfidCardDao = SpringContext.INSTANCE.getBean(RfidCardDao.class);
        CardNumber cardNumber = SpringContext.INSTANCE.getBean(CardNumber.class);

        for (RfidCard card : rfidCardDao.findAll()) {
            if (card.getCardNumber() == null) {
                card.setCardNumber(cardNumber.generate());
                rfidCardDao.save(card);
                writer.println("Assigned new number: " + card.getCardNumber());
            } else {
                writer.println("Number already existed: " + card.getCardNumber());
            }
        }
    }

    public static void fixCardIndexing(PrintWriter writer) {
        RfidCardDao rfidCardDao = SpringContext.INSTANCE.getBean(RfidCardDao.class);
        for (RfidCard card : rfidCardDao.findAll()) {
            rfidCardDao.save(card);
            writer.println("Zapisano: " + card + " | " + card.getCardNumber());
        }
    }

    public static void fixCardOverprint(PrintWriter writer) {
        RfidCardDao rfidCardDao = SpringContext.INSTANCE.getBean(RfidCardDao.class);
        for (RfidCard card : rfidCardDao.findAll()) {
            card.setOverprint(2389010);
            rfidCardDao.save(card);
            writer.println("Zapisano: " + card + " | " + card.getCardNumber());
        }
    }
}
