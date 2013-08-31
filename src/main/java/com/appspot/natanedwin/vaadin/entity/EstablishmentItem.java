package com.appspot.natanedwin.vaadin.entity;

import com.appspot.natanedwin.dao.Dao;
import com.appspot.natanedwin.dao.EstablishmentDao;
import com.appspot.natanedwin.entity.Establishment;
import com.appspot.natanedwin.service.spring.SpringContext;
import com.appspot.natanedwin.vaadin.EntityItem;
import com.vaadin.data.util.MethodProperty;

public class EstablishmentItem extends EntityItem<Establishment> {

    public EstablishmentItem(Establishment bean) {
        super(bean);
        addItemProperty("Nazwa", new MethodProperty(bean, "name"));
        addItemProperty("Ważność licencji", new MethodProperty(bean, "licenseValidity"));
        addItemProperty("NIP", new MethodProperty(bean, "plNip"));
    }

    @Override
    public Dao getDao() {
        return SpringContext.INSTANCE.getBean(EstablishmentDao.class);
    }
}
