package com.appspot.natanedwin.app.menu.kg;

import com.appspot.natanedwin.app.AppNavigator;
import com.appspot.natanedwin.service.appsession.AppSessionHelper;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;

/**
 *
 * @author prokob01
 */
public class ShowKinderGarten implements MenuBar.Command {

    @Override
    public void menuSelected(MenuItem selectedItem) {
        AppNavigator appNavigator = AppSessionHelper.getAppNavigator();
        appNavigator.navigateTo(AppNavigator.ViewDestination.KG);
    }
}
