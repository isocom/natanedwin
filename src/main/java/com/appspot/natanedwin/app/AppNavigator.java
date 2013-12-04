package com.appspot.natanedwin.app;

import com.appspot.natanedwin.app.view.AccessControlView;
import com.appspot.natanedwin.app.view.BogusView;
import com.appspot.natanedwin.app.view.HomeView;
import com.appspot.natanedwin.app.view.TimeAttendanceView;
import com.appspot.natanedwin.app.view.TransactionsView;
import com.appspot.natanedwin.app.view.UserAccountView;
import com.vaadin.navigator.Navigator;
import com.vaadin.ui.ComponentContainer;

/**
 *
 * @author Bart
 */
public class AppNavigator extends Navigator {

    public enum ViewDestination {

        HOME("", HomeView.class),
        AC("/ac", AccessControlView.class),
        BOGUS("/bogus", BogusView.class),
        RPC("/rpc", TimeAttendanceView.class),
        TRANSACTIONS("/transactions", TransactionsView.class),
        USERS("/users", UserAccountView.class);

        private final String route;
        private final Class classView;

        private ViewDestination(String route, Class classView) {
            this.route = route;
            this.classView = classView;
        }
    }

    public AppNavigator(AppUI ui, ComponentContainer container) {
        super(ui, container);

        for (ViewDestination v : ViewDestination.values()) {
            this.addView(v.route, v.classView);
        }
    }

    public void navigateTo(ViewDestination destination) {

        super.navigateTo(destination.route);
    }

}
