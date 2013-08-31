/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appspot.natanedwin.app.menu.superadmin;

import com.appspot.natanedwin.dao.RfidCardDao;
import com.appspot.natanedwin.entity.Human;
import com.appspot.natanedwin.entity.RfidCard;
import com.appspot.natanedwin.service.spring.SpringContext;
import com.appspot.natanedwin.vaadin.EntityAction;

/**
 *
 * @author rr163240
 */
public class AssignCardToHuman2 implements EntityAction<RfidCard> {

    private final Human human;

    public AssignCardToHuman2(final Human human) {
        this.human = human;
    }

    @Override
    public void execute(final RfidCard rfidCard) {
        RfidCardDao rfidCardDao = SpringContext.INSTANCE.getBean(RfidCardDao.class);
        rfidCardDao.assignHuman(rfidCard, human);
    }
}