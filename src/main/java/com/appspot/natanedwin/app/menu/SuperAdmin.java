package com.appspot.natanedwin.app.menu;

import com.appspot.natanedwin.app.menu.superadmin.AddDevice;
import com.appspot.natanedwin.app.menu.superadmin.AddEstablishment;
import com.appspot.natanedwin.app.menu.superadmin.AddHuman;
import com.appspot.natanedwin.app.menu.superadmin.AddRfidCard;
import com.appspot.natanedwin.app.menu.superadmin.AddUserAccount;
import com.appspot.natanedwin.app.menu.superadmin.ShowDevices;
import com.appspot.natanedwin.app.menu.superadmin.ShowEstablishments;
import com.appspot.natanedwin.app.menu.superadmin.ShowGcsFiles;
import com.appspot.natanedwin.app.menu.superadmin.ShowHumans;
import com.appspot.natanedwin.app.menu.superadmin.ShowRfidCards;
import com.appspot.natanedwin.app.menu.superadmin.ShowUserAccounts;
import com.appspot.natanedwin.service.mailer.MailerTester;
import com.vaadin.ui.MenuBar;

/**
 *
 * @author prokob01
 */
public class SuperAdmin {

    public static void buildMenuSuperAdmin(MenuBar menuBar) {
        MenuBar.MenuItem menu = menuBar.addItem("Superadmin", null);

        MenuBar.MenuItem menuAdd = menu.addItem("Dodaj", null);
        menuAdd.addItem("Dodaj konto użytkownika", new AddUserAccount());
        menuAdd.addItem("Dodaj urządzenie", new AddDevice());
        menuAdd.addItem("Dodaj organizacje", new AddEstablishment());
        menuAdd.addItem("Dodaj osobę fizyczną", new AddHuman());
        menuAdd.addItem("Dodaj kartę transponderową", new AddRfidCard());

        MenuBar.MenuItem menuShow = menu.addItem("Pokaż", null);
        menuShow.addItem("Pokaż karty transponderowe", new ShowRfidCards());
        menuShow.addItem("Pokaż konta użytkowników", new ShowUserAccounts());
        menuShow.addItem("Pokaż organizacje", new ShowEstablishments());
        menuShow.addItem("Pokaż osoby", new ShowHumans());
        menuShow.addItem("Pokaż pliki", new ShowGcsFiles());
        menuShow.addItem("Pokaż urządzenia", new ShowDevices());

        menu.addSeparator();
        menu.addItem("Testuj system mailera", new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                MailerTester.doTest();
            }
        });
    }
}
