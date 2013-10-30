package com.appspot.natanedwin.app;

import com.appspot.natanedwin.app.view.AccessControlView;
import com.appspot.natanedwin.app.view.BogusView;
import com.appspot.natanedwin.app.view.HomeView;
import com.appspot.natanedwin.app.view.TimeAttendanceView;
import com.appspot.natanedwin.app.view.TransactionsView;
import com.appspot.natanedwin.app.view.UserAccountView;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.ui.VerticalLayout;
import java.util.HashMap;

/**
 *
 * @author Bart
 */
public class AppNavigator extends Navigator {

    public static final String AC = "/ac";
    public static final String BOGUS = "/bogus";
    public static final String HOME = "/home";
    public static final String RPC = "/rpc";
    public static final String TRANSACTIONS = "/transactions";
    public static final String USERS = "/users";
    private final static HashMap<String, Class<? extends View>> navigatorRoutes = new HashMap<String, Class<? extends View>>() {
        {
            put(AC, AccessControlView.class);
            put(BOGUS, BogusView.class);
            put(HOME, HomeView.class);
            put(RPC, TimeAttendanceView.class);
            put(TRANSACTIONS, TransactionsView.class);
            put(USERS, UserAccountView.class);
        }
    };

    public AppNavigator(AppUI ui, VerticalLayout container) {
        super(ui, container);

        for (String route : navigatorRoutes.keySet()) {
            this.addView(route, navigatorRoutes.get(route));
        }
    }
}
