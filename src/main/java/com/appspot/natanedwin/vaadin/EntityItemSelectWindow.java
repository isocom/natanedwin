package com.appspot.natanedwin.vaadin;

import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 *
 * @author rr163240
 */
public class EntityItemSelectWindow extends Window {
    
    public static void showWindow(EntityContainer entityContainer, EntityAction entityAction) {
        UI current = UI.getCurrent();
        current.addWindow(new EntityItemSelectWindow(entityContainer, entityAction));
    }
    private final Table table;
    private final EntityContainer entityContainer;
    
    private EntityItemSelectWindow(final EntityContainer entityContainer, final EntityAction entityAction) {
        super("Wybierz jeden z poniższych obiektów");
        this.entityContainer = entityContainer;
        
        VerticalLayout verticalLayout = new VerticalLayout();
        
        table = new Table("Lista obiektów");
        table.setContainerDataSource(entityContainer);
        table.setSelectable(true);
        verticalLayout.addComponent(table);
        
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        
        horizontalLayout.addComponent(new Button("Wybierz", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                Object value = table.getValue();
                if (value == null) {
                    Notification.show("Wybierz najpierw element z listy");
                    return;
                }
                EntityItem item = (EntityItem) entityContainer.getItem(value);
                entityAction.execute(item.getEntity());
                close();
            }
        }));
        
        horizontalLayout.addComponent(new Button("Poniechaj", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                close();
            }
        }));
        
        verticalLayout.addComponent(horizontalLayout);
        setContent(verticalLayout);
        center();
        setModal(true);
    }
}
