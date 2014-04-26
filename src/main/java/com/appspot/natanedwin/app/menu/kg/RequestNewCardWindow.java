package com.appspot.natanedwin.app.menu.kg;

import com.appspot.natanedwin.dao.EstablishmentDao;
import com.appspot.natanedwin.dao.HumanDao;
import com.appspot.natanedwin.dao.RfidCardDao;
import com.appspot.natanedwin.entity.Establishment;
import com.appspot.natanedwin.entity.Human;
import com.appspot.natanedwin.entity.RfidCard;
import com.appspot.natanedwin.entity.RfidCardNature;
import com.appspot.natanedwin.service.appsession.AppSession;
import com.appspot.natanedwin.service.cardnumber.CardNumber;
import com.appspot.natanedwin.service.mailer.Email;
import com.appspot.natanedwin.service.mailer.EmailAddress;
import com.appspot.natanedwin.service.mailer.Mailer;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

/**
 *
 * @author prokob01
 */
@Configurable
public class RequestNewCardWindow extends Window {
    
    static final long serialVersionUID = -7064451567749202971L;
    @Autowired
    private transient AppSession appSession;
    @Autowired
    private transient CardNumber cardNumber;
    @Autowired
    private transient EstablishmentDao establishmentDao;
    @Autowired
    private transient HumanDao humanDao;
    @Autowired
    private transient Mailer mailer;
    @Autowired
    private transient RfidCardDao rfidCardDao;
    
    public static RequestNewCardWindow showWindow() {
        UI current = UI.getCurrent();
        RequestNewCardWindow w = new RequestNewCardWindow();
        current.addWindow(w);
        return w;
    }
    
    public RequestNewCardWindow() {
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setMargin(true);
        
        final TextField humanNameTF = new TextField("Imię i nazwisko");
        humanNameTF.setInputPrompt("Jan Kowalski");
        verticalLayout.addComponent(humanNameTF);
        
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.addComponent(new Button("Zamawiam", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                String imie = (String) humanNameTF.getValue();
                imie = imie.trim();
                if ("".equals(imie)) {
                    Notification.show("Wprowadź niepuste imię !!!");
                    return;
                }
                create(imie);
                close();
            }
        }));
        horizontalLayout.addComponent(new Button("Poniechaj", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                close();
            }
        }));
        
        verticalLayout.addComponent(horizontalLayout);
        setContent(verticalLayout);
        center();
    }
    
    private void create(String imieNazwisko) {
        final Human human = new Human();
        human.setName(imieNazwisko);
        human.setMonthlyRate(0L);
        humanDao.save(human);
        
        Establishment establishment = appSession.getEstablishment();
        establishment = establishmentDao.byId(establishment.getId());
        establishment.safeHumans();
        establishment.addHuman(human);
        establishmentDao.save(establishment);
        
        RfidCard rfidCard = new RfidCard();
        rfidCard.setCardNumber(cardNumber.generate());
        rfidCard.setRfidCardNature(RfidCardNature.Kindergarten);
        rfidCard.setHuman(human);
        if (establishment.getId() == 4776770016378880L) {
            rfidCard.setOverprint(5145621807759360L);
        }
        if (establishment.getId() == 6592287857442816L) {
            rfidCard.setOverprint(6608497064017920L);
        }
        if (establishment.getId() == 5709941587312640L) {
            rfidCard.setOverprint(5680735809699840L);
        }
        rfidCard.setRemarks("Żądanie z APPki");
        rfidCardDao.save(rfidCard);
        
        Email email = new Email();
        email.addTo(new EmailAddress("Karolina", "karolina.wysocka.prokop@gmail.com"));
        email.addTo(new EmailAddress("Ola", "aleksandra.korczynska.prokop@gmail.com"));
        email.addTo(new EmailAddress("Bart", "prokop.bart@gmail.com"));
        email.addCc(new EmailAddress("Zamawiający", appSession.getUserAccount().getEmail().getEmail()));
        email.addBcc(new EmailAddress("ISOCOM", "edziecko@isocom.eu"));
        email.setSubject("Karta NOWA - " + establishment.getName());
        email.setTextBody("Do wydruku i zakodowania karta o numerze: " + rfidCard.getCardNumber()
                + "\nOsoba: " + human.getName() + "\n\n"
                + "rfidCard:" + rfidCard.getId() + "\n"
                + "human:" + human.getId() + "\n");
        mailer.send(email);
    }
}
