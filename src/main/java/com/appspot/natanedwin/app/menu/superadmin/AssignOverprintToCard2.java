/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appspot.natanedwin.app.menu.superadmin;

import com.appspot.natanedwin.dao.RfidCardDao;
import com.appspot.natanedwin.entity.GcsFile;
import com.appspot.natanedwin.entity.RfidCard;
import com.appspot.natanedwin.service.spring.SpringContext;
import com.appspot.natanedwin.vaadin.EntityAction;

/**
 *
 * @author rr163240
 */
public class AssignOverprintToCard2 implements EntityAction<GcsFile> {

    private final RfidCard rfidCard;

    public AssignOverprintToCard2(final RfidCard rfidCard) {
        this.rfidCard = rfidCard;
    }

    @Override
    public void execute(final GcsFile overprint) {
        RfidCardDao rfidCardDao = SpringContext.INSTANCE.getBean(RfidCardDao.class);
        rfidCardDao.assignOverprint(rfidCard, overprint);
    }
}