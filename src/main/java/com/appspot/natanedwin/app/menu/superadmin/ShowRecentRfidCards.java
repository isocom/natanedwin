package com.appspot.natanedwin.app.menu.superadmin;

import com.appspot.natanedwin.dao.EstablishmentDao;
import com.appspot.natanedwin.dao.GcsFileDao;
import com.appspot.natanedwin.dao.HumanDao;
import com.appspot.natanedwin.dao.RfidCardDao;
import com.appspot.natanedwin.entity.Establishment;
import com.appspot.natanedwin.entity.GcsFile;
import com.appspot.natanedwin.entity.Human;
import com.appspot.natanedwin.entity.RfidCard;
import com.appspot.natanedwin.service.appsession.AppSession;
import com.appspot.natanedwin.vaadin.EntityAction;
import com.appspot.natanedwin.vaadin.EntityContainerWindow;
import com.appspot.natanedwin.vaadin.EntityContainer;
import com.appspot.natanedwin.vaadin.EntityItemSelectWindow;
import com.appspot.natanedwin.vaadin.entity.GcsFileItem2;
import com.appspot.natanedwin.vaadin.entity.HumanItem;
import com.appspot.natanedwin.vaadin.entity.RfidCardItem;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Notification;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

/**
 *
 * @author prokob01
 */
@Configurable
public class ShowRecentRfidCards implements MenuBar.Command {

    private final Map<String, EntityAction> additionalActions = new LinkedHashMap<>();
    @Autowired
    private transient AppSession appSession;
    @Autowired
    private transient GcsFileDao gcsFileDao;
    @Autowired
    private transient EstablishmentDao establishmentDao;
    @Autowired
    private transient RfidCardDao rfidCardDao;
    @Autowired
    private transient HumanDao humanDao;

    public ShowRecentRfidCards() {
        additionalActions.put("Drukuj kartę", new PrintCard());
        additionalActions.put("Przypisz wzór", new AssignOverprint());
        additionalActions.put("Przypisz do osoby", new AssignHuman1());
        additionalActions.put("Przypisz do ostatniej osoby", new AssignHuman2());
    }

    @Override
    public void menuSelected(MenuItem selectedItem) {
        EntityContainerWindow.showWindow(new EntityContainer<>(rfidCardDao.findRecent(15), RfidCardItem.class), additionalActions);
    }

    class AssignOverprint implements EntityAction<RfidCard> {

        @Override
        public void execute(final RfidCard rfidCard) {
            List<GcsFile> files = gcsFileDao.findAll();
            EntityContainer<GcsFile> entityContainer = new EntityContainer<>(files, GcsFileItem2.class);
            EntityItemSelectWindow.showWindow(entityContainer, new EntityAction<GcsFile>() {

                @Override
                public void execute(GcsFile overprint) {
                    rfidCardDao.assignOverprint(rfidCard, overprint);
                }
            });
        }

    }

    class AssignHuman1 implements EntityAction<RfidCard> {

        @Override
        public void execute(final RfidCard rfidCard) {
            if (rfidCard.getHuman() != null) {
                String name = humanDao.byRef(rfidCard.getHuman()).getName();
                Notification.show("Osoba już jest przypisana", name, Notification.Type.HUMANIZED_MESSAGE);
                return;
            }
            Establishment establishment = appSession.getEstablishment();
            establishment = establishmentDao.byId(establishment.getId());
            List<Human> humans = establishment.safeHumans();
            EntityContainer<Human> entityContainer = new EntityContainer<>(humans, HumanItem.class);
            EntityItemSelectWindow.showWindow(entityContainer, new EntityAction<Human>() {

                @Override
                public void execute(Human human) {
                    rfidCard.setHuman(human);
                    rfidCardDao.save(rfidCard);
                }
            });
        }

    }

    class AssignHuman2 implements EntityAction<RfidCard> {

        @Override
        public void execute(final RfidCard rfidCard) {
            if (rfidCard.getHuman() != null) {
                String name = humanDao.byRef(rfidCard.getHuman()).getName();
                Notification.show("Osoba już jest przypisana", name, Notification.Type.HUMANIZED_MESSAGE);
                return;
            }
            Establishment establishment = appSession.getEstablishment();
            establishment = establishmentDao.byId(establishment.getId());
            List<Human> humans = establishment.safeHumans();
            Collections.sort(humans, new Comparator<Human>() {

                @Override
                public int compare(Human o1, Human o2) {
                    return -o1.getFirstTimeSeen().compareTo(o2.getFirstTimeSeen());
                }
            });
            EntityContainer<Human> entityContainer = new EntityContainer<>(humans, HumanItem.class);
            EntityItemSelectWindow.showWindow(entityContainer, new EntityAction<Human>() {

                @Override
                public void execute(Human human) {
                    rfidCard.setHuman(human);
                    rfidCardDao.save(rfidCard);
                }
            });
        }

    }
}
