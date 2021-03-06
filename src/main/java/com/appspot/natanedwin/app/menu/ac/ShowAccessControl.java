package com.appspot.natanedwin.app.menu.ac;

import com.appspot.natanedwin.app.AppNavigator;
import com.appspot.natanedwin.service.appsession.AppSessionHelper;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;

/**
 *
 * @author prokob01
 */
public class ShowAccessControl implements MenuBar.Command {

    static final long serialVersionUID = -6696445644366731342L;

    @Override
    public void menuSelected(MenuItem selectedItem) {
        AppNavigator appNavigator = AppSessionHelper.getAppNavigator();
        appNavigator.navigateTo(AppNavigator.ViewDestination.AC);
    }
}
