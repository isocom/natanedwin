package com.appspot.natanedwin.app.menu.superadmin;

import com.appspot.natanedwin.app.AppError;
import com.appspot.natanedwin.entity.GcsFile;
import com.appspot.natanedwin.entity.Human;
import com.appspot.natanedwin.entity.RfidCard;
import com.appspot.natanedwin.report.ByteArrayStreamResource;
import com.appspot.natanedwin.service.appsession.AppSession;
import com.appspot.natanedwin.service.gcs.Gcs;
import com.appspot.natanedwin.service.ofy.Ofy;
import com.appspot.natanedwin.service.spring.SpringContext;
import com.appspot.natanedwin.vaadin.EntityAction;
import com.googlecode.objectify.Ref;
import com.pdfjet.example.Example_03_CC;
import com.vaadin.server.BrowserWindowOpener;
import com.vaadin.server.StreamResource;
import com.vaadin.ui.Link;

public class PrintCard implements EntityAction<RfidCard> {

    @Override
    public void execute(RfidCard entity) {
        String fileName = entity.getCardNumber() + ".pdf";
        String humanName;
        byte[] overprintData;

        Ofy ofy = SpringContext.INSTANCE.getBean(Ofy.class);
        Gcs gcs = SpringContext.INSTANCE.getBean(Gcs.class);
        Ref<Human> humanRef = entity.getHuman();
        if (humanRef != null) {
            Human human = ofy.ofy().load().key(humanRef.getKey()).now();
            humanName = human.getName();
        } else {
            throw new AppError("Brak osoby", "Przypisz najpierw osobę do tej karty");
        }

        Ref<GcsFile> overprintRef = entity.getOverprint();
        if (overprintRef != null) {
            GcsFile overprint = ofy.ofy().load().key(overprintRef.getKey()).now();
            overprintData = gcs.read(overprint.getBucketName(), overprint.getObjectName());
        } else {
            throw new AppError("Brak wzoru", "Przypisz najpierw wzór wydruku do tej karty");
        }

        ByteArrayStreamResource pdf = new ByteArrayStreamResource(Example_03_CC.karta(humanName, entity.getCardNumber(), overprintData));
        StreamResource resource = new StreamResource(pdf, fileName);
        BrowserWindowOpener opener = new BrowserWindowOpener(resource);
        Link link = new Link();
        link.setCaption(fileName);
        opener.extend(link);

        AppSession appSession = SpringContext.INSTANCE.getBean(AppSession.class);
        appSession.getAppUI().getDownloadArea().add(link);
    }
}
