package com.appspot.natanedwin.vaadin.entity;

import com.appspot.natanedwin.dao.Dao;
import com.appspot.natanedwin.dao.UserAccountDao;
import com.appspot.natanedwin.entity.UserAccount;
import com.appspot.natanedwin.service.spring.SpringContext;
import com.appspot.natanedwin.vaadin.EntityItem;
import com.vaadin.data.util.MethodProperty;

public class UserAccountItem extends EntityItem<UserAccount> {

    public UserAccountItem(UserAccount bean) {
        super(bean);
        addItemProperty("Typ konta", new MethodProperty(bean, "userAccountType"));
        addItemProperty("Identyfikator użytkownika", new MethodProperty(bean, "userId"));
        addItemProperty("E-mail", new MethodProperty(bean, "email"));
        addItemProperty("Strefa czasowa", new MethodProperty(bean, "dateTimeZone"));
        addItemProperty("Lokalizacja", new MethodProperty(bean, "locale"));
    }

    private void produceEmail(UserAccount userAccount) {
    }

    @Override
    public Dao getDao() {
        return SpringContext.INSTANCE.getBean(UserAccountDao.class);
    }

    @Override
    public String getEntityDescription() {
        return "Konto użytkownika";
    }
}
