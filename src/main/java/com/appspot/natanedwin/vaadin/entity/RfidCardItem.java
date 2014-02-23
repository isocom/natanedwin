package com.appspot.natanedwin.vaadin.entity;

import com.appspot.natanedwin.dao.Dao;
import com.appspot.natanedwin.dao.RfidCardDao;
import com.appspot.natanedwin.entity.RfidCard;
import com.appspot.natanedwin.service.cardnumber.CardNumber;
import com.appspot.natanedwin.vaadin.EntityItem;
import com.vaadin.data.util.MethodProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

@Configurable(preConstruction = true)
public class RfidCardItem extends EntityItem<RfidCard> {

    static final long serialVersionUID = -5767377125701102873L;
    @Autowired
    private transient CardNumber cardNumber;
    @Autowired
    private transient RfidCardDao rfidCardDao;

    public RfidCardItem(RfidCard bean) {
        super(bean);
        if (bean.getId() == null && bean.getCardNumber() == null) {
            bean.setCardNumber(cardNumber.generate());
        }
        addItemProperty("Numer karty", new MethodProperty(bean, "cardNumber"));
        addItemProperty("Typ karty", new MethodProperty(bean, "rfidCardType"));
        addItemProperty("Natura karty", new MethodProperty(bean, "rfidCardNature"));
        addItemProperty("Numer seryjny", new MethodProperty(bean, "serialNumber"));
    }

    @Override
    public Dao getDao() {
        return rfidCardDao;
    }

    @Override
    public String getEntityDescription() {
        return "Karta transponderowa";
    }
}
