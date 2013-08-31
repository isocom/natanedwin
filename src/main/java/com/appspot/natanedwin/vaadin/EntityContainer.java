package com.appspot.natanedwin.vaadin;

import com.appspot.natanedwin.app.AppError;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class EntityContainer<BT> implements Container {

    private final Class<? extends EntityItem<BT>> entityItemClass;
    private final Class<?> entityClass;
    private final Map<Long, EntityItem<BT>> entityItems = new LinkedHashMap<Long, EntityItem<BT>>();
    private final EntityItem<BT> topEntityItem;

    public EntityContainer(List<BT> entityList, Class<? extends EntityItem<BT>> entityItemClass) {
        if (entityList.isEmpty()) {
            throw new AppError("entityList is empty", "Spróbuj najpierw dodać pojedynczy element. Nie można wyświetlić pustej listy.");
        }
        this.entityItemClass = entityItemClass;
        initMap(entityList);
        this.topEntityItem = entityItems.values().iterator().next();
        this.entityClass = topEntityItem.getEntityClass();
    }

    private void initMap(List<BT> entityList) {
        for (BT bean : entityList) {
            try {
                Constructor<? extends EntityItem<BT>> constructor = entityItemClass.getConstructor(bean.getClass());
                EntityItem<BT> item = constructor.newInstance(bean);
                entityItems.put(item.getEntityId(), item);
            } catch (Throwable t) {
                throw new RuntimeException("Unable to wrap entities", t);
            }
        }
    }

    @Override
    public Item getItem(Object itemId) {
        return entityItems.get((Long) itemId);
    }

    @Override
    public Collection<String> getContainerPropertyIds() {
        return topEntityItem.getItemPropertyIds();
    }

    @Override
    public Collection<Long> getItemIds() {
        return entityItems.keySet();
    }

    @Override
    public Property getContainerProperty(Object itemId, Object propertyId) {
        Item item = getItem(itemId);
        return item.getItemProperty(propertyId);
    }

    @Override
    public Class<?> getType(Object propertyId) {
        return topEntityItem.getItemProperty(propertyId).getType();
    }

    @Override
    public int size() {
        return entityItems.size();
    }

    @Override
    public boolean containsId(Object itemId) {
        return entityItems.containsKey((Long) itemId);
    }

    @Override
    public Item addItem(Object itemId) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object addItem() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean removeItem(Object itemId) throws UnsupportedOperationException {
        return entityItems.remove((Long) itemId) != null;
    }

    @Override
    public boolean addContainerProperty(Object propertyId, Class<?> type, Object defaultValue) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean removeContainerProperty(Object propertyId) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean removeAllItems() throws UnsupportedOperationException {
        entityItems.clear();
        return true;
    }

    ////////////////////////////////////////////////////////////////////////////
    // Some helpers
    ////////////////////////////////////////////////////////////////////////////
    public void addEntityItem(EntityItem entityItem) {
        entityItems.put(111L, entityItem);
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public Class<? extends EntityItem<BT>> getEntityItemClass() {
        return entityItemClass;
    }

    public Class<?> getEntityClass() {
        return entityClass;
    }
    ////////////////////////////////////////////////////////////////////////////
    // Resizing the container after construction
    ////////////////////////////////////////////////////////////////////////////
//    @Override
//    public void addItemSetChangeListener(ItemSetChangeListener listener) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//    
//    @Override
//    public void addListener(ItemSetChangeListener listener) {
//        addItemSetChangeListener(listener);
//    }
//    
//    @Override
//    public void removeItemSetChangeListener(ItemSetChangeListener listener) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//    
//    @Override
//    public void removeListener(ItemSetChangeListener listener) {
//        removeItemSetChangeListener(listener);
//    }
}