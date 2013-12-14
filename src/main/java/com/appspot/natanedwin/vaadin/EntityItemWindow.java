package com.appspot.natanedwin.vaadin;

import com.appspot.natanedwin.dao.Dao;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Field;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/**
 *
 * @author prokob01
 */
public class EntityItemWindow extends Window {

    public static EntityItemWindow showWindow(EntityItem entityItem) {
        return showWindow(entityItem, Collections.EMPTY_MAP);
    }

    public static EntityItemWindow showWindow(EntityItem entityItem, Map<String, EntityAction> additionalActions) {
        UI current = UI.getCurrent();
        EntityItemWindow entityItemWindow = new EntityItemWindow(entityItem, additionalActions);
        current.addWindow(entityItemWindow);
        return entityItemWindow;
    }
    private final EntityItem entityItem;

    private EntityItemWindow(EntityItem entityWrapper, Map<String, EntityAction> additionalActions) {
        this.entityItem = entityWrapper;
        if (entityWrapper.getEntityId() == null) {
            setCaption("Nowy obiekt: " + entityWrapper.getEntityDescription());
        } else {
            setCaption("Edycja obiektu: " + entityWrapper.getEntityDescription());
        }
        setContent(new EntityForm(additionalActions));
        center();
    }

    private void onPersist() {
        Dao dao = entityItem.getDao();
        dao.save(entityItem.getEntity());
        close();
    }

    private void onCancel() {
        close();
    }

    private void onAdditionalAction(EntityAction action) {
        action.execute(entityItem.getEntity());
    }

    private class EntityForm extends CustomComponent {

        public EntityForm(final Map<String, EntityAction> additionalActions) {
            VerticalLayout verticalLayout = new VerticalLayout();
            verticalLayout.setMargin(true);

            FormLayout formLayout = new FormLayout();
            Collection<String> itemPropertyIds = entityItem.getItemPropertyIds();
            for (String pid : itemPropertyIds) {
                formLayout.addComponent(buildField(pid));
            }

            HorizontalLayout horizontalLayout = new HorizontalLayout();
            horizontalLayout.addComponent(new Button("Zapisz", new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent event) {
                    onPersist();
                }
            }));
            horizontalLayout.addComponent(new Button("Poniechaj", new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent event) {
                    onCancel();
                }
            }));
            for (final String caption : additionalActions.keySet()) {
                horizontalLayout.addComponent(new Button(caption, new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent event) {
                        onAdditionalAction(additionalActions.get(caption));
                    }
                }));
            }

            verticalLayout.addComponent(formLayout);
            verticalLayout.addComponent(horizontalLayout);
            setCompositionRoot(verticalLayout);
        }

        private Field buildField(String pid) {
            Field field;
            switch (entityItem.renderingHint(pid)) {
                case CheckBox:
                    field = new CheckBox();
                    break;
                default:
                    field = new TextField();
                    break;
            }
            field.setCaption(pid);
            field.setPropertyDataSource(entityItem.getItemProperty(pid));
            return field;
        }
    }

    public EntityItem getEntityItem() {
        return entityItem;
    }
}
