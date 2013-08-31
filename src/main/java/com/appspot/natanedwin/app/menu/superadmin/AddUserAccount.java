/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appspot.natanedwin.app.menu.superadmin;

import com.appspot.natanedwin.entity.UserAccount;
import com.appspot.natanedwin.entity.UserAccountType;
import com.appspot.natanedwin.vaadin.EntityItemWindow;
import com.appspot.natanedwin.vaadin.entity.UserAccountItem;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;

/**
 *
 * @author prokob01
 */
public class AddUserAccount implements MenuBar.Command {
    
    @Override
    public void menuSelected(MenuItem selectedItem) {
        UserAccount userAccount = new UserAccount();
        userAccount.setUserAccountType(UserAccountType.Internal);
        EntityItemWindow.showWindow(new UserAccountItem(userAccount));
    }
}
