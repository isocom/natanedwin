/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appspot.natanedwin.vaadin.entity;

import com.appspot.natanedwin.dao.Dao;
import com.appspot.natanedwin.dao.DeviceDao;
import com.appspot.natanedwin.entity.Device;
import com.appspot.natanedwin.service.spring.SpringContext;
import com.appspot.natanedwin.vaadin.EntityItem;

public class DeviceItem extends EntityItem<Device> {

    public DeviceItem(Device bean) {
        super(bean);

        addItemMethodProperty("Numer seryjny", "serialNumber");
        addItemMethodProperty("Opis", "description");
    }

    @Override
    public Dao getDao() {
        return SpringContext.INSTANCE.getBean(DeviceDao.class);
    }

    @Override
    public String getEntityDescription() {
        return "Urządzenie";
    }
}
