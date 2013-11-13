package com.appspot.natanedwin.vaadin;

import com.vaadin.data.Item;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import java.lang.reflect.Constructor;
import java.util.Collections;
import java.util.Map;

public class EntityContainerWindow extends Window {

    public static void showWindow(EntityContainer entityContainer) {
        showWindow(entityContainer, false, Collections.EMPTY_MAP);
    }

    public static void showWindowEditOnly(EntityContainer entityContainer) {
        showWindow(entityContainer, true, Collections.EMPTY_MAP);
    }

    public static void showWindow(EntityContainer entityContainer, Map<String, EntityAction> additionalActions) {
        UI current = UI.getCurrent();
        current.addWindow(new EntityContainerWindow(entityContainer, false, additionalActions));
    }

    public static void showWindow(EntityContainer entityContainer, boolean editOnly, Map<String, EntityAction> additionalActions) {
        UI current = UI.getCurrent();
        current.addWindow(new EntityContainerWindow(entityContainer, editOnly, additionalActions));
    }
    private final Table table;
    private final EntityContainer entityContainer;

    private EntityContainerWindow(final EntityContainer entityContainer, final boolean editOnly, final Map<String, EntityAction> additionalActions) {
        super("Lista obiektów typu: ");
        this.entityContainer = entityContainer;

        VerticalLayout verticalLayout = new VerticalLayout();

        table = new Table("Lista obiektów");
        table.setContainerDataSource(entityContainer);
        table.setSelectable(true);
        verticalLayout.addComponent(table);

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.addComponent(new Button("Zamknij", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                close();
            }
        }));

        if (!editOnly) {
            horizontalLayout.addComponent(new Button("Nowy", new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent event) {
                    newButtonClicked();
                }
            }));
        }

        horizontalLayout.addComponent(new Button("Edytuj", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                editButtonClicked();
            }
        }));

        if (!editOnly) {
            horizontalLayout.addComponent(new Button("Usuń", new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent event) {
                    deleteButtonClicked();
                }
            }));
        }

        for (final String caption : additionalActions.keySet()) {
            horizontalLayout.addComponent(new Button(caption, new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent event) {
                    additionalActionClicked(additionalActions.get(caption));
                }
            }));
        }

        verticalLayout.addComponent(horizontalLayout);
        setContent(verticalLayout);
        center();
    }

    private void additionalActionClicked(EntityAction action) {
        Object value = table.getValue();
        if (value == null) {
            Notification.show("Wybierz najpierw wiersz w tabelce");
            return;
        }
        EntityItem item = (EntityItem) entityContainer.getItem(value);
        action.execute(item.getEntity());
    }

    private void newButtonClicked() {
        try {
            Object newEntity = entityContainer.getEntityClass().newInstance();
            Constructor entityItemConstructor = entityContainer.getEntityItemClass().getConstructor(entityContainer.getEntityClass());
            EntityItem entityItem = (EntityItem) entityItemConstructor.newInstance(newEntity);
            EntityItemWindow entityItemWindow = EntityItemWindow.showWindow(entityItem);

            entityContainer.addEntityItem(entityItem);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void editButtonClicked() {
        Item item = entityContainer.getItem(table.getValue());
        EntityItemWindow.showWindow((EntityItem) item);
    }

    private void deleteButtonClicked() {
        EntityItem item = (EntityItem) entityContainer.getItem(table.getValue());
        entityContainer.removeItem(item.getEntityId());
        item.getDao().delete(item.getEntity());
    }
}
