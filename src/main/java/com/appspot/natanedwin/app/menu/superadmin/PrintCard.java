package com.appspot.natanedwin.app.menu.superadmin;

import com.appspot.natanedwin.entity.Human;
import com.appspot.natanedwin.entity.RfidCard;
import com.appspot.natanedwin.report.ByteArrayStreamResource;
import com.appspot.natanedwin.service.appsession.AppSession;
import com.appspot.natanedwin.service.ofy.Ofy;
import com.appspot.natanedwin.service.spring.SpringContext;
import com.appspot.natanedwin.vaadin.EntityAction;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.pdfjet.example.Example_03_CC;
import com.vaadin.server.BrowserWindowOpener;
import com.vaadin.server.StreamResource;
import com.vaadin.ui.Link;

public class PrintCard implements EntityAction<RfidCard> {

    @Override
    public void execute(RfidCard entity) {
        String fileName = entity.getCardNumber() + ".pdf";
        String humanName = "Nieprzypisany";

        Ofy ofy = SpringContext.INSTANCE.getBean(Ofy.class);
        Ref<Human> humanRef = entity.getHuman();
        if (humanRef != null) {
            Key< Human> humanKey = humanRef.getKey();
            Human human = ofy.ofy().load().key(humanKey).now();
            humanName = human.getName();
        }
        
        ByteArrayStreamResource pdf = new ByteArrayStreamResource(Example_03_CC.karta(humanName, entity.getCardNumber()));
        StreamResource resource = new StreamResource(pdf, fileName);
        BrowserWindowOpener opener = new BrowserWindowOpener(resource);
        Link link = new Link();
        link.setCaption(fileName);
        opener.extend(link);

        AppSession appSession = SpringContext.INSTANCE.getBean(AppSession.class);
        appSession.getAppUI().getDownloadArea().add(link);
    }
}
