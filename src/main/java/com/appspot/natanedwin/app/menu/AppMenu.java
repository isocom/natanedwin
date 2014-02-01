package com.appspot.natanedwin.app.menu;

import com.appspot.natanedwin.app.AppAboutWindow;
import com.appspot.natanedwin.app.AppNavigator;
import com.appspot.natanedwin.app.menu.profile.ChangePassword;
import com.appspot.natanedwin.service.appsession.AppSession;
import com.appspot.natanedwin.service.appsession.AppSessionHelper;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

/**
 *
 * @author prokob01
 */
@Configurable(preConstruction = true)
public final class AppMenu extends MenuBar {

    @Autowired
    private transient AppSession appSession;

    public AppMenu() {
        setWidth("100%");
        buildMenuView();
        buildMenuProfil();
        buildMenus();
        addItem("Zakończ", new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                appSession.shutdown();
            }
        });
    }
    
    private void buildMenus() {
        FilesMenu.build(this);
        AccessControlMenu.build(this);
        KinderGartenMenu.build(this);
        TimeAttendanceMenu.build(this);
        if (appSession.getUserCredentials().isUserAdmin()) {
            SuperAdmin.buildMenuSuperAdmin(this);
        }
    }

    private void buildMenuProfil() {
        MenuItem menu = addItem("Profil", null);
        MenuItem menuItem;

        menuItem = menu.addItem("Zmień hasło dostępu", new ChangePassword());
        menuItem.setEnabled(true);
        menuItem = menu.addItem("Edytuj mój profil", new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem selectedItem) {
            }
        });
        menuItem.setEnabled(false);

        menu.addSeparator();
        menuItem = menu.addItem("Usuń profil z systemu", new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem selectedItem) {
            }
        });
        menuItem.setEnabled(false);
        menu.addSeparator();
        menu.addItem("Informacja o systemie", new MenuBar.Command() {
            @Override
            public void menuSelected(MenuItem selectedItem) {
                AppAboutWindow.show();
            }
        });
    }

    private void buildMenuView() {
        MenuItem menu = addItem("Widok", null);

        menu.addItem("Panel główny", new MenuBar.Command() {

            @Override
            public void menuSelected(MenuItem selectedItem) {
                AppSessionHelper.getAppNavigator().navigateTo(AppNavigator.ViewDestination.HOME);
            }
        });
        menu.addSeparator();
        menu.addItem("Kontrola dostępu", new MenuBar.Command() {

            @Override
            public void menuSelected(MenuItem selectedItem) {
                AppSessionHelper.getAppNavigator().navigateTo(AppNavigator.ViewDestination.AC);
            }
        });
        menu.addItem("Przedszkole", new MenuBar.Command() {

            @Override
            public void menuSelected(MenuItem selectedItem) {
                AppSessionHelper.getAppNavigator().navigateTo(AppNavigator.ViewDestination.KG);
            }
        });
        menu.addItem("Rejestracja czasu pracy", new MenuBar.Command() {

            @Override
            public void menuSelected(MenuItem selectedItem) {
                AppSessionHelper.getAppNavigator().navigateTo(AppNavigator.ViewDestination.RPC);
            }
        });
    }
}
