package com.appspot.natanedwin.app.menu.superadmin;

import com.appspot.natanedwin.dao.EstablishmentDao;
import com.appspot.natanedwin.dao.HumanDao;
import com.appspot.natanedwin.entity.Establishment;
import com.appspot.natanedwin.entity.Human;
import com.appspot.natanedwin.service.spring.SpringContext;
import com.appspot.natanedwin.vaadin.EntityAction;
import com.appspot.natanedwin.vaadin.EntityContainerWindow;
import com.appspot.natanedwin.vaadin.EntityContainer;
import com.appspot.natanedwin.vaadin.EntityItemSelectWindow;
import com.appspot.natanedwin.vaadin.entity.EstablishmentItem;
import com.appspot.natanedwin.vaadin.entity.HumanItem;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

/**
 *
 * @author prokob01
 */
@Configurable
public class ShowRecentHumans implements MenuBar.Command {

    private final Map<String, EntityAction> additionalActions = new LinkedHashMap<>();
    @Autowired
    private transient EstablishmentDao establishmentDao;
    @Autowired
    private transient HumanDao humanDao;

    public ShowRecentHumans() {
        additionalActions.put("Przypisz kartę", new AssignCardToHuman1());
        additionalActions.put("Przypisz do podmiotu", new AddToEstablishment());
    }

    @Override
    public void menuSelected(MenuItem selectedItem) {
        EntityContainerWindow.showWindow(new EntityContainer<>(humanDao.findRecent(15), HumanItem.class), additionalActions);
    }

    class AddToEstablishment implements EntityAction<Human> {

        @Override
        public void execute(final Human human) {
            EntityContainer<Establishment> entityContainer = new EntityContainer<>(establishmentDao.findAll(), EstablishmentItem.class);
            EntityItemSelectWindow.showWindow(entityContainer, new EntityAction<Establishment>() {

                @Override
                public void execute(Establishment establishment) {
                    establishment = establishmentDao.byId(establishment.getId());
                    establishment.safeHumans();
                    establishment.addHuman(human);
                    establishmentDao.save(establishment);
                }
            });
        }

    }
}
