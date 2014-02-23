package com.appspot.natanedwin.app.menu.superadmin;

import com.appspot.natanedwin.entity.GcsFile;
import com.appspot.natanedwin.entity.Human;
import com.appspot.natanedwin.entity.RfidCard;
import com.appspot.natanedwin.report.ByteArrayStreamResource;
import com.appspot.natanedwin.service.appsession.AppSession;
import com.appspot.natanedwin.service.gcs.Gcs;
import com.appspot.natanedwin.service.ofy.Ofy;
import com.appspot.natanedwin.vaadin.EntityAction;
import com.googlecode.objectify.Ref;
import com.pdfjet.example.Example_03_CC;
import com.vaadin.server.BrowserWindowOpener;
import com.vaadin.server.StreamResource;
import com.vaadin.ui.Link;
import com.vaadin.ui.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

@Configurable
public class PrintCard implements EntityAction<RfidCard> {

static final long serialVersionUID = -8368429256084555956L;
    @Autowired
    private transient AppSession appSession;
    @Autowired
    private transient Ofy ofy;
    @Autowired
    private transient Gcs gcs;

    @Override
    public void execute(RfidCard entity) {
        String fileName = entity.getCardNumber() + ".pdf";
        String humanName;
        byte[] overprintData;

        Ref<Human> humanRef = entity.getHuman();
        if (humanRef != null) {
            Human human = ofy.ofy().load().key(humanRef.getKey()).now();
            humanName = human.getName();
        } else {
            Notification.show("Brak przypisanej osoby", Notification.Type.ERROR_MESSAGE);
            return;
        }

        Ref<GcsFile> overprintRef = entity.getOverprint();
        if (overprintRef != null) {
            GcsFile overprint = ofy.ofy().load().key(overprintRef.getKey()).now();
            overprintData = gcs.read(overprint.getBucketName(), overprint.getObjectName());
        } else {
            Notification.show("Brak przypisanego wzoru wydruku", Notification.Type.ERROR_MESSAGE);
            return;
        }

        ByteArrayStreamResource pdf = new ByteArrayStreamResource(Example_03_CC.karta(humanName, entity.getCardNumber(), overprintData));
        StreamResource resource = new StreamResource(pdf, fileName);
        BrowserWindowOpener opener = new BrowserWindowOpener(resource);
        Link link = new Link();
        link.setCaption(fileName);
        opener.extend(link);

        appSession.getAppUI().getDownloadArea().add(link);
    }
}
