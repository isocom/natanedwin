package com.appspot.natanedwin.app.menu;

import com.appspot.natanedwin.app.AppAboutWindow;
import com.appspot.natanedwin.app.menu.profile.ChangePassword;
import com.appspot.natanedwin.service.appsession.AppSession;
import com.appspot.natanedwin.service.spring.SpringContext;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.UI;

/**
 *
 * @author prokob01
 */
public final class AppMenu {

    private AppMenu() {
        throw new IllegalStateException();
    }

    public static MenuBar buildMainMenu() {
        AppSession appSession = SpringContext.INSTANCE.getBean(AppSession.class);
        final MenuBar menuBar = new MenuBar();
        menuBar.setWidth("100%");

        buildMenuProfil(menuBar);
        if (appSession.getUserCredentials().isUserAdmin()) {
            SuperAdmin.buildMenuSuperAdmin(menuBar);
        }

        menuBar.addItem("Zakończ", new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                AppSession appSession = SpringContext.INSTANCE.getBean(AppSession.class);
                appSession.shitdown();
            }
        });
        return menuBar;
    }

    private static void buildMenuProfil(MenuBar menuBar) {
        MenuItem menu = menuBar.addItem("Profil", null);
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
}
