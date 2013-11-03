package com.appspot.natanedwin.app.menu.superadmin;

import com.appspot.natanedwin.dao.UserAccountDao;
import com.appspot.natanedwin.entity.Establishment;
import com.appspot.natanedwin.entity.UserAccount;
import com.appspot.natanedwin.service.ofy.Ofy;
import com.appspot.natanedwin.service.spring.SpringContext;
import com.appspot.natanedwin.vaadin.EntityAction;
import com.appspot.natanedwin.vaadin.EntityContainerWindow;
import com.appspot.natanedwin.vaadin.EntityContainer;
import com.appspot.natanedwin.vaadin.EntityItemSelectWindow;
import com.appspot.natanedwin.vaadin.entity.EstablishmentItem;
import com.appspot.natanedwin.vaadin.entity.UserAccountItem;
import com.googlecode.objectify.cmd.Query;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author prokob01
 */
public class ShowEstablishments implements MenuBar.Command {

    private final Map<String, EntityAction> additionalActions = new LinkedHashMap<>();

    public ShowEstablishments() {
        additionalActions.put("Przypisz użytkownika", new AssignUserAccountToEstablishment1());
    }

    @Override
    public void menuSelected(MenuItem selectedItem) {
        Ofy ofy = SpringContext.INSTANCE.getBean(Ofy.class);
        Query<Establishment> query = ofy.ofy().load().type(Establishment.class);
        List<Establishment> list = query.list();
        ofy.ofy().clear();

        EntityContainerWindow.showWindow(new EntityContainer<>(list, EstablishmentItem.class), additionalActions);
    }

    private class AssignUserAccountToEstablishment1 implements EntityAction<Establishment> {

        @Override
        public void execute(Establishment entity) {
            UserAccountDao userAccountDao = SpringContext.INSTANCE.getBean(UserAccountDao.class);
            List<UserAccount> userAccounts = userAccountDao.findEstablishmentUnassigned();

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
            UserAccountDao userAccountDao = SpringContext.INSTANCE.getBean(UserAccountDao.class);
            userAccount = userAccountDao.byId(userAccount.getId());
            userAccount.setEstablishment(establishment);
            userAccountDao.save(userAccount);
        }
    }
}
