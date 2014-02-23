package com.appspot.natanedwin.app.menu.superadmin;

import com.appspot.natanedwin.dao.EstablishmentDao;
import com.appspot.natanedwin.dao.UserAccountDao;
import com.appspot.natanedwin.entity.Establishment;
import com.appspot.natanedwin.entity.UserAccount;
import com.appspot.natanedwin.service.spring.SpringContext;
import com.appspot.natanedwin.vaadin.EntityAction;
import com.appspot.natanedwin.vaadin.EntityContainerWindow;
import com.appspot.natanedwin.vaadin.EntityContainer;
import com.appspot.natanedwin.vaadin.EntityItemSelectWindow;
import com.appspot.natanedwin.vaadin.entity.EstablishmentItem;
import com.appspot.natanedwin.vaadin.entity.UserAccountItem;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author prokob01
 */
public class ShowUserAccounts implements MenuBar.Command {

static final long serialVersionUID = 4634489009555078877L;
    private final Map<String, EntityAction> additionalActions = new LinkedHashMap<>();

    public ShowUserAccounts() {
        additionalActions.put("Przypisz podmiot", new AssignUserAccountToEstablishment1());
        additionalActions.put("Resetuj hasło", new ResetPassword());
    }

    @Override
    public void menuSelected(MenuItem selectedItem) {
        UserAccountDao userAccountDao = SpringContext.INSTANCE.getBean(UserAccountDao.class);
        EntityContainerWindow.showWindow(new EntityContainer<>(userAccountDao.findAll(), UserAccountItem.class), additionalActions);
    }

    private static class AssignUserAccountToEstablishment1 implements EntityAction<UserAccount> {

        @Override
        public void execute(UserAccount entity) {
            EstablishmentDao establishmentDao = SpringContext.INSTANCE.getBean(EstablishmentDao.class);
            List<Establishment> establishments = establishmentDao.findAll();

            EntityContainer<Establishment> entityContainer = new EntityContainer<>(establishments, EstablishmentItem.class);
            EntityItemSelectWindow.showWindow(entityContainer, new AssignUserAccountToEstablishment2(entity));
        }

    }

    private static class AssignUserAccountToEstablishment2 implements EntityAction<Establishment> {

        private final UserAccount userAccount;

        public AssignUserAccountToEstablishment2(UserAccount userAccount) {
            this.userAccount = userAccount;
        }

        @Override
        public void execute(Establishment establishment) {
            UserAccountDao userAccountDao = SpringContext.INSTANCE.getBean(UserAccountDao.class);
            UserAccount u = userAccountDao.byId(userAccount.getId());
            u.setEstablishment(establishment);
            userAccountDao.save(u);
        }
    }

    private static class ResetPassword implements EntityAction<UserAccount> {

        @Override
        public void execute(UserAccount entity) {
            UserAccountDao userAccountDao = SpringContext.INSTANCE.getBean(UserAccountDao.class);
            userAccountDao.resetPassword(entity);
        }

    }
}
