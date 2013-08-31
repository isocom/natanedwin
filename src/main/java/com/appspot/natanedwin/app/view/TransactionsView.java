package com.appspot.natanedwin.app.view;

import com.appspot.natanedwin.dao.RfidEventDao;
import com.appspot.natanedwin.entity.RfidEvent;
import com.appspot.natanedwin.service.spring.SpringContext;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import java.util.List;

public class TransactionsView extends VerticalLayout implements View {

    public TransactionsView() {
        setSizeFull();

    }
    List<RfidEvent> find;
    BeanItemContainer<RfidEvent> beanItemContainer;

    @Override
    public void enter(ViewChangeEvent event) {
        RfidEventDao rfidEventDao = SpringContext.INSTANCE.getBean(RfidEventDao.class);
        find = rfidEventDao.find();
        beanItemContainer = new BeanItemContainer(RfidEvent.class, find);

        addTop();
        addTable();
        //        DataProvider dataProvider = ((DashboardUI) getUI()).dataProvider;
        //        t.setContainerDataSource(dataProvider.getRevenueByTitle());
    }

    private void addTop() {
        HorizontalLayout top = new HorizontalLayout();
        top.setWidth("100%");
        top.setSpacing(true);
        top.addStyleName("toolbar");
        addComponent(top);
        final Label title = new Label("Ostatnie zdarzenia");
        title.setSizeUndefined();
        title.addStyleName("h1");
        top.addComponent(title);
        top.setComponentAlignment(title, Alignment.MIDDLE_LEFT);
        top.setExpandRatio(title, 1);

        Button edit = new Button();
        edit.addStyleName("icon-edit");
        edit.addStyleName("icon-only");
        top.addComponent(edit);
        edit.setDescription("Edit Dashboard");
        edit.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                final Window w = new Window("Edit Dashboard");

                w.setModal(true);
                w.setClosable(false);
                w.setResizable(false);
                w.addStyleName("edit-dashboard");

                getUI().addWindow(w);

                w.setContent(new VerticalLayout() {
                    TextField name = new TextField("Dashboard Name");

                    {
                        addComponent(new FormLayout() {
                            {
                                setSizeUndefined();
                                setMargin(true);
                                name.setValue(title.getValue());
                                addComponent(name);
                                name.focus();
                                name.selectAll();
                            }
                        });

                        addComponent(new HorizontalLayout() {
                            {
                                setMargin(true);
                                setSpacing(true);
                                addStyleName("footer");
                                setWidth("100%");

                                Button cancel = new Button("Cancel");
                                cancel.addClickListener(new ClickListener() {
                                    @Override
                                    public void buttonClick(ClickEvent event) {
                                        w.close();
                                    }
                                });
                                cancel.setClickShortcut(KeyCode.ESCAPE, null);
                                addComponent(cancel);
                                setExpandRatio(cancel, 1);
                                setComponentAlignment(cancel,
                                        Alignment.TOP_RIGHT);

                                Button ok = new Button("Save");
                                ok.addStyleName("wide");
                                ok.addStyleName("default");
                                ok.addClickListener(new ClickListener() {
                                    @Override
                                    public void buttonClick(ClickEvent event) {
                                        title.setValue(name.getValue());
                                        w.close();
                                    }
                                });
                                ok.setClickShortcut(KeyCode.ENTER, null);
                                addComponent(ok);
                            }
                        });

                    }
                });

            }
        });
        top.setComponentAlignment(edit, Alignment.MIDDLE_LEFT);
    }

    private void addTable() {
        /* Create the table with a caption. */
        Table table = new Table("This is my Table");

        /* Define the names and data types of columns.
         * The "default value" parameter is meaningless here. */
        table.addContainerProperty("First Name", String.class, null);
        table.addContainerProperty("Last Name", String.class, null);
        table.addContainerProperty("Year", Integer.class, null);

        /* Add a few items in the table. */
        table.addItem(new Object[]{"Nicolaus", "Copernicus", new Integer(1473)}, new Integer(1));
        table.addItem(new Object[]{"Tycho", "Brahe", new Integer(1546)}, new Integer(2));
        table.addItem(new Object[]{"Giordano", "Bruno", new Integer(1548)}, new Integer(3));
        table.addItem(new Object[]{"Galileo", "Galilei", new Integer(1564)}, new Integer(4));
        table.addItem(new Object[]{"Johannes", "Kepler", new Integer(1571)}, new Integer(5));
        table.addItem(new Object[]{"Isaac", "Newton", new Integer(1643)}, new Integer(6));

//        table.setVisibleColumns(new String[]{"id", "rfidEventType", "eventDate"});
//        table.setContainerDataSource(beanItemContainer);

        addComponent(table);
    }
}
