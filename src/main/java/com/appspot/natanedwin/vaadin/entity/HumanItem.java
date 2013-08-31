package com.appspot.natanedwin.vaadin.entity;

import com.appspot.natanedwin.dao.Dao;
import com.appspot.natanedwin.dao.HumanDao;
import com.appspot.natanedwin.entity.Human;
import com.appspot.natanedwin.service.spring.SpringContext;
import com.appspot.natanedwin.vaadin.EntityItem;
import com.vaadin.data.util.MethodProperty;

public class HumanItem extends EntityItem<Human> {

    public HumanItem(Human bean) {
        super(bean);
        addItemProperty("Imię i nazwisko", new MethodProperty(bean, "name"));
    }

    @Override
    public Dao getDao() {
        return SpringContext.INSTANCE.getBean(HumanDao.class);
    }

    @Override
    public String getEntityDescription() {
        return "Osoba";
    }
}
