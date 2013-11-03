package com.appspot.natanedwin.app.menu.superadmin;

import com.appspot.natanedwin.entity.RfidCard;
import com.appspot.natanedwin.entity.RfidCardType;
import com.appspot.natanedwin.service.cardnumber.CardNumber;
import com.appspot.natanedwin.service.spring.SpringContext;
import com.appspot.natanedwin.vaadin.EntityItemWindow;
import com.appspot.natanedwin.vaadin.entity.RfidCardItem;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;

/**
 *
 * @author prokob01
 */
public class AddRfidCard implements MenuBar.Command {

    @Override
    public void menuSelected(MenuItem selectedItem) {
        String cardNumber = SpringContext.INSTANCE.getBean(CardNumber.class).generate();
        RfidCard rfidCard = new RfidCard();
        rfidCard.setRfidCardType(RfidCardType.Mifare1k);
        rfidCard.setCardNumber(cardNumber);
        EntityItemWindow.showWindow(new RfidCardItem(rfidCard));
    }
}
