package com.appspot.natanedwin.app.menu.superadmin;

import com.appspot.natanedwin.dao.EstablishmentDao;
import com.appspot.natanedwin.dao.HumanDao;
import com.appspot.natanedwin.dao.UserAccountDao;
import com.appspot.natanedwin.entity.Establishment;
import com.appspot.natanedwin.entity.Human;
import com.appspot.natanedwin.entity.UserAccount;
import com.appspot.natanedwin.vaadin.EntityAction;
import com.appspot.natanedwin.vaadin.EntityContainerWindow;
import com.appspot.natanedwin.vaadin.EntityContainer;
import com.appspot.natanedwin.vaadin.EntityItemSelectWindow;
import com.appspot.natanedwin.vaadin.entity.EstablishmentItem;
import com.appspot.natanedwin.vaadin.entity.HumanItem;
import com.appspot.natanedwin.vaadin.entity.UserAccountItem;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Notification;
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
public class ShowEstablishments implements MenuBar.Command {
    
    private final Map<String, EntityAction> additionalActions = new LinkedHashMap<>();
    @Autowired
    private transient EstablishmentDao establishmentDao;
    @Autowired
    private transient UserAccountDao userAccountDao;
    @Autowired
    private transient HumanDao humanDao;
    
    public ShowEstablishments() {
        additionalActions.put("Przypisz użytkownika", new AssignUserAccountToEstablishment1());
        additionalActions.put("Dopisz osobę", new AddHumanToEstablishment1());
    }
    
    @Override
    public void menuSelected(MenuItem selectedItem) {
        List<Establishment> list = establishmentDao.findAll();
        EntityContainerWindow.showWindow(new EntityContainer<>(list, EstablishmentItem.class), additionalActions);
    }
    
    private class AssignUserAccountToEstablishment1 implements EntityAction<Establishment> {
        
        @Override
        public void execute(Establishment entity) {
            List<UserAccount> userAccounts = userAccountDao.findEstablishmentUnassigned();
            if (userAccounts.isEmpty()) {
                Notification.show("Wszystkie konta użytkowników są już przypisane !!!");
                return;
            }
            
            EntityContainer<UserAccount> entityContainer = new EntityContainer<>(userAccounts, UserAccountItem.class);
            EntityItemSelectWindow.showWindow(entityContainer, new AssignUserAccountToEstablishment2(entity));
        }
        
    }
    
    private class AssignUserAccountToEstablishment2 implements EntityAction<UserAccount> {
        
        private final Establishment establishment;
        
        public AssignUserAccountToEstablishment2(Establishment establishment) {
            this.establishment = establishment;
        }
        
        @Override
        public void execute(UserAccount userAccount) {
            userAccount = userAccountDao.byId(userAccount.getId());
            userAccount.setEstablishment(establishment);
            userAccountDao.save(userAccount);
        }
    }
    
    private class AddHumanToEstablishment1 implements EntityAction<Establishment> {
        
        @Override
        public void execute(Establishment entity) {
            List<Human> humans = humanDao.findAll();
            EntityContainer<Human> entityContainer = new EntityContainer<>(humans, HumanItem.class);
            EntityItemSelectWindow.showWindow(entityContainer, new AddHumanToEstablishment2(entity));
        }
        
    }
    
    private class AddHumanToEstablishment2 implements EntityAction<Human> {
        
        private final Establishment establishment;
        
        public AddHumanToEstablishment2(Establishment establishment) {
            this.establishment = establishment;
        }
        
        @Override
        public void execute(Human human) {
            Establishment byId = establishmentDao.byId(establishment.getId());
            if (byId.safeHumans().contains(human)) {
                Notification.show("Ta osoba jest już dodana do tego podmiotu", Notification.Type.ERROR_MESSAGE);
                return;
            }
            byId.addHuman(human);
            establishmentDao.save(byId);
        }
    }
}
