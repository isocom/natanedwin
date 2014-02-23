package com.appspot.natanedwin.servlet.tr610;

import com.appspot.natanedwin.dao.HumanDao;
import com.appspot.natanedwin.dao.RfidCardDao;
import com.appspot.natanedwin.dao.RfidEventDao;
import com.appspot.natanedwin.entity.Device;
import com.appspot.natanedwin.entity.Human;
import com.appspot.natanedwin.entity.RfidCard;
import com.appspot.natanedwin.entity.RfidCardType;
import com.appspot.natanedwin.entity.RfidEvent;
import com.appspot.natanedwin.entity.RfidEventType;
import com.appspot.natanedwin.service.cardnumber.CardNumber;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CardDetected1 {

    @Autowired
    private transient CardNumber cardNumber;
    @Autowired
    private transient HumanDao humanDao;
    @Autowired
    private transient RfidCardDao rfidCardDao;
    @Autowired
    private transient RfidEventDao rfidEventDao;

    public void cardDetected1(final Device device, final HttpServletRequest req, final PrintWriter resp) {
        final String serialNumber = req.getParameter("cn");

        RfidCard rfidCard = rfidCardDao.findBySerialNumber(serialNumber);
        if (rfidCard == null) {
            newCard(resp, device, serialNumber);
            return;
        }

        if (rfidCard.getHuman() == null) {
            TR610Response.display2(resp, "Karta nieprzypisana", rfidCard.getCardNumber());
            return;
        }
        Human human = humanDao.byId(rfidCard.getHuman().getKey().getId());

        if (!human.isActive()) {
            TR610Response.display1(resp, "Osoba nieaktywna !!!");
            return;
        }

        switch (rfidCard.getRfidCardNature()) {
            case Regular:
                regular(device, req, resp, rfidCard, human);
                break;
            case Kindergarten:
                kindergarten(req, resp, rfidCard, human);
                break;
        }
    }

    private void newCard(PrintWriter resp, final Device device, final String serialNumber) {
        try {
            JSONObject conf = device.getJsonConfiguration();
            final String confkey = "handleNewCards";
            if (!conf.has(confkey)) {
                TR610Response.display3(resp, "UWAGA NOWA KARTA !!!", "Brak konfiguacji dla", confkey);
                return;
            }
            if (!conf.getBoolean(confkey)) {
                TR610Response.display2(resp, "UWAGA NOWA KARTA !!!", "Kodowanie wylaczone");
                return;
            }
        } catch (JSONException jsone) {
            return;
        }

        List<RfidCard> rfidCards = rfidCardDao.findRecent(20);
        rfidCards = new ArrayList<>(rfidCards);
        Collections.sort(rfidCards, new Comparator<RfidCard>() {

            @Override
            public int compare(RfidCard o1, RfidCard o2) {
                return o2.getFirstTimeSeen().compareTo(o1.getFirstTimeSeen());
            }
        });
        Iterator<RfidCard> iterator = rfidCards.iterator();
        while (iterator.hasNext()) {
            RfidCard next = iterator.next();
            if (next.getRfidCardType() != RfidCardType.Vacant) {
                iterator.remove();
            }
        }

        if (rfidCards.isEmpty()) {
            // no Vacant cards - just add a new card
            RfidCard rfidCard = new RfidCard();
            rfidCard.setRfidCardType(RfidCardType.Mifare1k);
            rfidCard.setSerialNumber(serialNumber);
            rfidCard.setCardNumber(cardNumber.generate());
            rfidCardDao.save(rfidCard);
            TR610Response.display2(resp, "Dodano karte", rfidCard.getCardNumber());
        } else {
            // encode the detected card to top Vacant card...
            RfidCard rfidCard = rfidCards.get(0);
            rfidCard.setRfidCardType(RfidCardType.Mifare1k);
            rfidCard.setSerialNumber(serialNumber);
            rfidCardDao.save(rfidCard);
            TR610Response.display2(resp, "Zakodowano", rfidCard.getCardNumber());
        }
    }

    private void regular(Device device, HttpServletRequest req, PrintWriter resp, RfidCard rfidCard, Human human) {
        final RfidEventType mode = RfidEventType.valueOf(req.getParameter("mode"));

        RfidEvent rfidEvent = new RfidEvent();
        rfidEvent.setDevice(device);
        rfidEvent.setRfidCard(rfidCard);
        rfidEvent.setHuman(rfidCard.getHuman());
        rfidEvent.setRfidEventType(mode);
        rfidEventDao.save(rfidEvent);

        String line1;
        switch (mode) {
            case In:
                line1 = "Witaj";
                break;
            case Out:
                line1 = "Do widzenia";
                break;
            case TempIn:
                line1 = "Witaj ponownie";
                break;
            case TempOut:
                line1 = "Do zobaczenia pozniej";
                break;
            default:
                line1 = "---";
                break;
        }
        String line2 = human.getName();
        TR610Response.display3(resp, line1, line2, rfidCard.getCardNumber());
    }

    private void kindergarten(HttpServletRequest req, PrintWriter resp, RfidCard rfidCard, Human human) {
        String cn = rfidCard.getCardNumber();
        cn = cardNumber.compress(cn);
        String line4 = "ERROR";

        List<RfidCard> rfidCards = rfidCardDao.findByHuman(human);
        StringBuilder sb = new StringBuilder();
        for (RfidCard card : rfidCards) {
            sb.append(cardNumber.compress(card.getCardNumber())).append(",");
        }

        try {
            URL url = new URL("http://e-dziecko.appspot.com/NatanEdwin?cn=" + cn + "&cns=" + sb);
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "US-ASCII"))) {
                line4 = reader.readLine();
            }
        } catch (IOException e) {
            if (e.getMessage() != null) {
                line4 = e.getMessage();
                if (line4.length() > 25) {
                    line4 = line4.substring(0, 25);
                }
            }
        }
        if (line4 == null) {
            line4 = "NULL";
        }
        TR610Response.display4(resp, "Przedszkolak", human.getName(), rfidCard.getCardNumber(), line4);
    }

}
