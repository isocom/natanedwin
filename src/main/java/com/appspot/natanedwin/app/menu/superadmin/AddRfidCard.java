package com.appspot.natanedwin.app.menu.superadmin;

import com.appspot.natanedwin.entity.RfidCard;
import com.appspot.natanedwin.service.cardnumber.CardNumber;
import com.appspot.natanedwin.vaadin.EntityItemWindow;
import com.appspot.natanedwin.vaadin.entity.RfidCardItem;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

/**
 *
 * @author prokob01
 */
@Configurable
public class AddRfidCard implements MenuBar.Command {

    @Autowired
    private transient CardNumber cardNumber;

    @Override
    public void menuSelected(MenuItem selectedItem) {
        RfidCard rfidCard = new RfidCard();
        rfidCard.setCardNumber(cardNumber.generate());
        EntityItemWindow.showWindow(new RfidCardItem(rfidCard));
    }
}
