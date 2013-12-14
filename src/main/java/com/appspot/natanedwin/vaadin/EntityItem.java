package com.appspot.natanedwin.vaadin;

import com.appspot.natanedwin.dao.Dao;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.MethodProperty;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

/**
 *
 * @param <BT> POJO (Bean that is used do wrap)
 */
public abstract class EntityItem<BT> implements Item {

    private final static String ID_PID = "GAE ID";
    private final static String UUID_PID = "UUID";

    public enum RenderingHint {

        TextField, CheckBox;
    }
    /**
     * The bean which this Item is based on.
     */
    protected final BT entity;
    private final Class<BT> entityClass;
    private final MethodProperty idProperty;
    private final MethodProperty uuidProperty;
    /**
     * Mapping from property id to property.
     */
    private final HashMap<Object, Property<?>> propertyMap = new HashMap<>();
    /**
     * Mapping from property id to rendering hint.
     */
    private final HashMap<Object, RenderingHint> renderingHintMap = new HashMap<>();
    /**
     * List of all property ids to maintain the order.
     */
    private final LinkedList<String> propertyList = new LinkedList<>();

    public EntityItem(BT bean) {
        this.entity = bean;
        this.entityClass = (Class<BT>) bean.getClass();

        idProperty = new MethodProperty(bean, "id");
        idProperty.setReadOnly(true);
        addItemProperty(ID_PID, idProperty);

        uuidProperty = new MethodProperty(bean, "uuid");
        uuidProperty.setReadOnly(true);
        addItemProperty(UUID_PID, uuidProperty);
    }

    @Override
    public final Property getItemProperty(Object id) {
        return propertyMap.get(id);
    }

    @Override
    public final Collection<String> getItemPropertyIds() {
        return propertyList;
    }

    public final boolean addItemMethodProperty(String pid, String beanPropertyName) {
        return addItemProperty(pid, new MethodProperty(this.entity, beanPropertyName));
    }

    @Override
    public final boolean addItemProperty(Object id, Property property) throws UnsupportedOperationException {
        propertyMap.put(id, property);
        renderingHintMap.put(id, RenderingHint.TextField);
        return propertyList.add(id.toString());
    }

    public final boolean addItemProperty(Object id, Property property, RenderingHint renderingHint) throws UnsupportedOperationException {
        propertyMap.put(id, property);
        renderingHintMap.put(id, renderingHint);
        return propertyList.add(id.toString());
    }

    @Override
    public boolean removeItemProperty(Object id) throws UnsupportedOperationException {
        propertyMap.remove(id);
        return propertyList.remove(id.toString());
    }

    public RenderingHint renderingHint(String pid) {
        return renderingHintMap.get(pid);
    }

    public abstract Dao getDao();

    public String getEntityDescription() {
        return entityClass.getCanonicalName();
    }

    public final Class<BT> getEntityClass() {
        return entityClass;
    }

    public final BT getEntity() {
        return entity;
    }

    public final Long getEntityId() {
        return (Long) idProperty.getValue();
    }

    public final String getEntityUuid() {
        return (String) uuidProperty.getValue();
    }
}
