package com.appspot.natanedwin.vaadin.entity;

import com.appspot.natanedwin.dao.Dao;
import com.appspot.natanedwin.dao.RfidCardDao;
import com.appspot.natanedwin.entity.RfidCard;
import com.appspot.natanedwin.service.spring.SpringContext;
import com.appspot.natanedwin.vaadin.EntityItem;
import com.vaadin.data.util.MethodProperty;

public class RfidCardItem extends EntityItem<RfidCard> {

    public RfidCardItem(RfidCard bean) {
        super(bean);
        addItemProperty("Typ karty", new MethodProperty(bean, "rfidCardType"));
        addItemProperty("Natura karty", new MethodProperty(bean, "rfidCardNature"));
        addItemProperty("Numer seryjny", new MethodProperty(bean, "serialNumber"));
        addItemProperty("Numer karty", new MethodProperty(bean, "cardNumber"));
    }

    @Override
    public Dao getDao() {
        return SpringContext.INSTANCE.getBean(RfidCardDao.class);
    }

    @Override
    public String getEntityDescription() {
        return "Karta transponderowa";
    }
}
