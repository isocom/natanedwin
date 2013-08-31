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
        addItemProperty("Numer seryjny", new MethodProperty(bean, "serialNumber"));
        addItemProperty("Numer karty", new MethodProperty(bean, "cardNumber"));
//        if (getEntityId() == null) {
//            Ofy ofy = SpringContext.INSTANCE.getBean(Ofy.class);
//            Query<RfidCard> query = ofy.ofy().load().type(RfidCard.class).filter("serialNumber", getEntity().getSerialNumber());
//            RfidCard rfidCard = query.first().get();
//            if (rfidCard != null) {
//                throw new EntityHelperException("Istnieje już karta o numerze seryjnym: " + getEntity().getSerialNumber());
//            }
//            query = ofy.ofy().load().type(RfidCard.class).filter("cardNumber", getEntity().getSerialNumber());
//            rfidCard = query.first().get();
//            if (rfidCard != null) {
//                throw new EntityHelperException("Istnieje już karta o numerze: " + getEntity().getCardNumber());
//            }
//
//            if (getEntity().getRfidCardType() == null) {
//                throw new EntityHelperException("Nieznany typ karty");
//            }
//        }
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
