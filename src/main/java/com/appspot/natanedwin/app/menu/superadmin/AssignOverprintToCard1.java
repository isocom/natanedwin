/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appspot.natanedwin.app.menu.superadmin;

import com.appspot.natanedwin.dao.GcsFileDao;
import com.appspot.natanedwin.entity.GcsFile;
import com.appspot.natanedwin.entity.RfidCard;
import com.appspot.natanedwin.service.spring.SpringContext;
import com.appspot.natanedwin.vaadin.EntityAction;
import com.appspot.natanedwin.vaadin.EntityContainer;
import com.appspot.natanedwin.vaadin.EntityItemSelectWindow;
import com.appspot.natanedwin.vaadin.entity.GcsFileItem;
import java.util.List;

/**
 *
 * @author rr163240
 */
public class AssignOverprintToCard1 implements EntityAction<RfidCard> {

    @Override
    public void execute(final RfidCard card) {
        final GcsFileDao gcsFileDao = SpringContext.INSTANCE.getBean(GcsFileDao.class);
        List<GcsFile> files = gcsFileDao.findAll();

        EntityContainer<GcsFile> entityContainer = new EntityContainer<>(files, GcsFileItem.class);
        EntityItemSelectWindow.showWindow(entityContainer, new AssignOverprintToCard2(card));
    }
}
