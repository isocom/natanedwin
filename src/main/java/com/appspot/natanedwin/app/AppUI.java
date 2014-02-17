package com.appspot.natanedwin.app;

import com.appspot.natanedwin.app.menu.AppMenu;
import com.appspot.natanedwin.vaadin.converter.CustomConverterFactory;
import com.appspot.natanedwin.service.appsession.AppSession;
import com.appspot.natanedwin.service.spring.SpringContext;
import com.appspot.natanedwin.service.user.UserCredentials;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.WrappedSession;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author prokob01
 */
public class AppUI extends UI {

    private final String WRAPPED_SESSION_USER_CREDENTIALS = "userCredentials";
    private final static Logger LOGGER = LoggerFactory.getLogger(AppUI.class);
    private AppNavigator appNavigator;
    private DownloadArea downloadArea;
    private AppMenu appMenu;
    private SideBar sideBar;

    @Override
    protected void init(VaadinRequest request) {
        // get login information - from http session
        WrappedSession wrappedSession = request.getWrappedSession(false);
        if (wrappedSession == null) {
            Notification.show("No http session - no play", Notification.Type.ERROR_MESSAGE);
            return;
        }
        UserCredentials userCredentials = (UserCredentials) wrappedSession.getAttribute(WRAPPED_SESSION_USER_CREDENTIALS);
        if (userCredentials == null) {
            Notification.show("No credentials\nSorry - no play", Notification.Type.ERROR_MESSAGE);
            return;
        }
        // clean session credentials
        wrappedSession.removeAttribute(WRAPPED_SESSION_USER_CREDENTIALS);

        if (getSession() == null) {
            Notification.show("No vaadin app session", Notification.Type.ERROR_MESSAGE);
            return;
        }
        LOGGER.info("Zalogowano: " + userCredentials);

        AppSession appSession = SpringContext.INSTANCE.getBean(AppSession.class);
        appSession.parseUserCredentials(userCredentials);

        // OK, we can fire application, as we have user credentials
        appSession.getVaadinSession().setErrorHandler(new AppErrorListener());
        appSession.getVaadinSession().setConverterFactory(new CustomConverterFactory());

        getPage().setTitle("ISOCOM");
        setContent(buildContent());
    }

    private VerticalLayout buildContent() {
        VerticalLayout content = new VerticalLayout();
        content.setSizeFull();

        appMenu = new AppMenu();
        content.addComponent(appMenu);

        HorizontalLayout centerArea = new HorizontalLayout();
        centerArea.setSizeFull();
        
        sideBar = new SideBar();
        centerArea.addComponent(sideBar);
        
        VerticalLayout navigatorArea = new VerticalLayout();
        navigatorArea.setSizeFull();
        appNavigator = new AppNavigator(this, navigatorArea);
        centerArea.addComponent(navigatorArea);
        centerArea.setExpandRatio(navigatorArea, 1);

        content.addComponent(centerArea);
        content.setExpandRatio(centerArea, 1);
        
        downloadArea = new DownloadArea();
        content.addComponent(downloadArea);

        return content;
    }

    // User menu
    //        sideBar.addComponent(new VerticalLayout() {
    //            {
    //                setSizeUndefined();
    //                addStyleName("user");
    //                Image profilePic = new Image(null, new ClassResource("profile-pic.png"));
    //                profilePic.setWidth("34px");
    //                addComponent(profilePic);
    //                Label userName = new Label("Bart Prokop");
    //                userName.setSizeUndefined();
    //                addComponent(userName);
    //
    //                Command cmd = new Command() {
    //                    @Override
    //                    public void menuSelected(MenuItem selectedItem) {
    //                        Notification.show("Not implemented in this demo");
    //                    }
    //                };
    //                MenuBar settings = new MenuBar();
    //                MenuItem settingsMenu = settings.addItem("", new ClassResource("settings-pic.png"), null);
    //                settingsMenu.setStyleName("icon-cog");
    //                settingsMenu.addItem("Settings", cmd);
    //                settingsMenu.addItem("Preferences", cmd);
    //                settingsMenu.addSeparator();
    //                settingsMenu.addItem("My Account", cmd);
    //                addComponent(settings);
    //
    //                Button exit = new NativeButton();
    //                exit.setIcon(new ClassResource("close-pic.png"));
    //                exit.setDescription("Zamknij aplikację");
    //                addComponent(exit);
    //                exit.addClickListener(new ClickListener() {
    //                    @Override
    //                    public void buttonClick(ClickEvent event) {
    //                        UI.getCurrent().getPage().setLocation("/");
    //                    }
    //                });
    //            }
    //        });
    public DownloadArea getDownloadArea() {
        return downloadArea;
    }

    public AppNavigator getAppNavigator() {
        return appNavigator;
    }

    public AppMenu getAppMenu() {
        return appMenu;
    }

}
