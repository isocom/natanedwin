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
import com.appspot.natanedwin.vaadin.EntityContainer;
import com.appspot.natanedwin.vaadin.EntityItemSelectWindow;
import com.appspot.natanedwin.vaadin.entity.RfidCardItem;
import com.vaadin.ui.Notification;
import java.util.List;

/**
 *
 * @author rr163240
 */
public class AssignCardToHuman1 implements EntityAction<Human> {

    @Override
    public void execute(final Human human) {
        final RfidCardDao rfidCardDao = SpringContext.INSTANCE.getBean(RfidCardDao.class);
        List<RfidCard> unassignedCards = rfidCardDao.findHumanUnassigned();
        if (unassignedCards.isEmpty()) {
            Notification.show("Nie ma nieprzypisanych kart w systemie", Notification.Type.WARNING_MESSAGE);
            return;
        }

        EntityContainer<RfidCard> entityContainer = new EntityContainer<RfidCard>(unassignedCards, RfidCardItem.class);
        EntityItemSelectWindow.showWindow(entityContainer, new AssignCardToHuman2(human));
    }
}
