package com.appspot.natanedwin.app.menu.kg;

import com.appspot.natanedwin.dao.EstablishmentDao;
import com.appspot.natanedwin.dao.RfidCardDao;
import com.appspot.natanedwin.entity.Establishment;
import com.appspot.natanedwin.entity.Human;
import com.appspot.natanedwin.entity.RfidCard;
import com.appspot.natanedwin.entity.RfidCardNature;
import com.appspot.natanedwin.service.appsession.AppSession;
import com.appspot.natanedwin.service.cardnumber.CardNumber;
import com.appspot.natanedwin.vaadin.EntityAction;
import com.appspot.natanedwin.vaadin.EntityContainer;
import com.appspot.natanedwin.vaadin.EntityItemSelectWindow;
import com.appspot.natanedwin.vaadin.entity.HumanItem;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

/**
 *
 * @author prokob01
 */
@Configurable
public class RequestAdditionalCard implements MenuBar.Command {

    @Autowired
    private transient AppSession appSession;
    @Autowired
    private transient CardNumber cardNumber;
    @Autowired
    private transient EstablishmentDao establishmentDao;
    @Autowired
    private transient RfidCardDao rfidCardDao;

    @Override
    public void menuSelected(MenuItem selectedItem) {
        final Establishment establishment = appSession.getEstablishment();
        Establishment e = establishmentDao.byId(establishment.getId());
        List<Human> humans = e.safeHumans();
        EntityContainer<Human> entityContainer = new EntityContainer<>(humans, HumanItem.class);
        EntityItemSelectWindow.showWindow(entityContainer, new EntityAction<Human>() {

            @Override
            public void execute(Human human) {
                RfidCard rfidCard = new RfidCard();
                rfidCard.setCardNumber(cardNumber.generate());
                rfidCard.setRfidCardNature(RfidCardNature.Kindergarten);
                rfidCard.setHuman(human);
                if (establishment.getId() == 4776770016378880L) {
                    rfidCard.setOverprint(5145621807759360L);
                }
                if (establishment.getId() == 6592287857442816L) {
                    rfidCard.setOverprint(6608497064017920L);
                }
                if (establishment.getId() == 5709941587312640L) {
                    rfidCard.setOverprint(5680735809699840L);
                }
                rfidCard.setRemarks("Żądanie z APPki");
                rfidCardDao.save(rfidCard);
            }
        });

    }
}
