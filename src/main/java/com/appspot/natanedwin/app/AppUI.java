package com.appspot.natanedwin.app;

import com.appspot.natanedwin.app.menu.AppMenu;
import com.appspot.natanedwin.vaadin.converter.CustomConverterFactory;
import com.appspot.natanedwin.service.appsession.AppSession;
import com.appspot.natanedwin.service.spring.SpringContext;
import com.appspot.natanedwin.service.user.UserCredentials;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.WrappedSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.MenuBar;
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
        appSession.setUserCredentials(userCredentials);

        // OK, we can fire application, as we have user credentials
        appSession.getVaadinSession().setErrorHandler(new AppErrorListener());
        appSession.getVaadinSession().setConverterFactory(new CustomConverterFactory());

        getPage().setTitle("ISOCOM");
        setContent(buildContent());
    }

    private VerticalLayout buildContent() {
        VerticalLayout content = new VerticalLayout();
        content.setSizeFull();

        MenuBar menuBar = AppMenu.buildMainMenu();
        content.addComponent(menuBar);

        HorizontalLayout centerArea = buildCenterArea();
        content.addComponent(centerArea);
        content.setExpandRatio(centerArea, 1);

        return content;
    }

    private HorizontalLayout buildCenterArea() {
        HorizontalLayout centerArea = new HorizontalLayout();
        centerArea.setSizeFull();
        centerArea.addComponent(buildSideBar());

        VerticalLayout content = new VerticalLayout();
        appNavigator = new AppNavigator(this, content);

        centerArea.addComponent(content);
        centerArea.setExpandRatio(content, 1);

        return centerArea;
    }

    private VerticalLayout buildSideBar() {
        final float buttonWidth = 120;

        VerticalLayout sideBar = new VerticalLayout();
        sideBar.setWidth(null);
        sideBar.setHeight("100%");
        sideBar.setSpacing(false);

        Button button;

        button = new Button("Panel główny", new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                appNavigator.navigateTo(AppNavigator.ViewDestination.HOME);
            }
        });
        button.setWidth(buttonWidth, Unit.PIXELS);
        sideBar.addComponent(button);

        button = new Button("RCP", new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                appNavigator.navigateTo(AppNavigator.ViewDestination.RPC);
            }
        });
        button.setWidth(buttonWidth, Unit.PIXELS);
        sideBar.addComponent(button);

        button = new Button("KD", new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                appNavigator.navigateTo(AppNavigator.ViewDestination.BOGUS);
            }
        });
        button.setWidth(buttonWidth, Unit.PIXELS);
        sideBar.addComponent(button);

        button = new Button("Parking", new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                appNavigator.navigateTo(AppNavigator.ViewDestination.BOGUS);
            }
        });
        button.setWidth(buttonWidth, Unit.PIXELS);
        sideBar.addComponent(button);

        button = new Button("Hotel", new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                appNavigator.navigateTo(AppNavigator.ViewDestination.BOGUS);
            }
        });
        button.setWidth(buttonWidth, Unit.PIXELS);
        sideBar.addComponent(button);

        button = new Button("Bilety", new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                appNavigator.navigateTo(AppNavigator.ViewDestination.BOGUS);
            }
        });
        button.setWidth(buttonWidth, Unit.PIXELS);
        sideBar.addComponent(button);

        button = new Button("Basen", new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                appNavigator.navigateTo(AppNavigator.ViewDestination.BOGUS);
            }
        });
        button.setWidth(buttonWidth, Unit.PIXELS);
        sideBar.addComponent(button);

        button = new Button("Siłownia", new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                appNavigator.navigateTo(AppNavigator.ViewDestination.BOGUS);
            }
        });
        button.setWidth(buttonWidth, Unit.PIXELS);
        sideBar.addComponent(button);

        button = new Button("Sprzedaż", new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                appNavigator.navigateTo(AppNavigator.ViewDestination.BOGUS);
            }
        });
        button.setWidth(buttonWidth, Unit.PIXELS);
        sideBar.addComponent(button);

        downloadArea = new DownloadArea();
//        downloadArea.setSizeFull();
        sideBar.addComponent(downloadArea);
        sideBar.setExpandRatio(downloadArea, 1);
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
        return sideBar;
    }

    public DownloadArea getDownloadArea() {
        return downloadArea;
    }

    public AppNavigator getAppNavigator() {
        return appNavigator;
    }

}
