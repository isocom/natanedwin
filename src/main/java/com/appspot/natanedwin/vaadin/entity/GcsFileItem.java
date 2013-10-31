package com.appspot.natanedwin.vaadin.entity;

import com.appspot.natanedwin.dao.Dao;
import com.appspot.natanedwin.dao.GcsFileDao;
import com.appspot.natanedwin.entity.GcsFile;
import com.appspot.natanedwin.service.spring.SpringContext;
import com.appspot.natanedwin.vaadin.EntityItem;
import com.vaadin.data.util.MethodProperty;

public class GcsFileItem extends EntityItem<GcsFile> {

    public GcsFileItem(GcsFile bean) {
        super(bean);
        addItemProperty("Koszyk", new MethodProperty(bean, "bucketName"));
        addItemProperty("Nazwa pliku", new MethodProperty(bean, "objectName"));
        addItemProperty("md5 hash", new MethodProperty(bean, "md5sum"));
        addItemProperty("sha1 hash", new MethodProperty(bean, "sha1sum"));
        addItemProperty("opis", new MethodProperty(bean, "description"));
    }

    @Override
    public Dao getDao() {
        return SpringContext.INSTANCE.getBean(GcsFileDao.class);
    }

    @Override
    public String getEntityDescription() {
        return "Plik w chmurze";
    }
}
