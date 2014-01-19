package com.appspot.natanedwin.servlet.fix;

import com.appspot.natanedwin.dao.EstablishmentDao;
import com.appspot.natanedwin.dao.HumanDao;
import com.appspot.natanedwin.dao.RfidCardDao;
import com.appspot.natanedwin.entity.Establishment;
import com.appspot.natanedwin.entity.Human;
import com.appspot.natanedwin.entity.RfidCard;
import com.appspot.natanedwin.entity.RfidCardNature;
import com.appspot.natanedwin.entity.RfidCardType;
import com.appspot.natanedwin.service.cardnumber.CardNumber;
import com.appspot.natanedwin.service.spring.SpringContext;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class RfidCardFix {

    public static void fixAssignCardNoNumber(PrintWriter writer) {
        RfidCardDao rfidCardDao = SpringContext.INSTANCE.getBean(RfidCardDao.class);
        CardNumber cardNumber = SpringContext.INSTANCE.getBean(CardNumber.class);

        for (RfidCard card : rfidCardDao.findAll()) {
            if (card.getCardNumber() == null) {
                card.setCardNumber(cardNumber.generate());
                rfidCardDao.save(card);
                writer.println("Assigned new number: " + card.getCardNumber());
            } else {
                writer.println("Number already existed: " + card.getCardNumber());
            }
        }
    }

    public static void fixReSave(PrintWriter writer) {
        RfidCardDao rfidCardDao = SpringContext.INSTANCE.getBean(RfidCardDao.class);
        for (RfidCard card : rfidCardDao.findAll()) {
            rfidCardDao.save(card);
            writer.println("Zapisano: " + card + " | " + card.getCardNumber());
        }
    }

    public static void fixCardOverprint(PrintWriter writer) {
        RfidCardDao rfidCardDao = SpringContext.INSTANCE.getBean(RfidCardDao.class);
        for (RfidCard card : rfidCardDao.findAll()) {
            card.setOverprint(2389010);
            rfidCardDao.save(card);
            writer.println("Zapisano: " + card + " | " + card.getCardNumber());
        }
    }

    /**
     * First you shall export whole database for przsedszkole:
     * view-source:https://e-dziecko.appspot.com/ExportujKD
     *
     * Then create a new Establishment and put it's ID here... Upload card
     * printout, and notice ID of uploaded file Put GCSFile id down here
     *
     * @param writer
     * @throws Exception
     */
    public static void addPrzedszkole1(PrintWriter writer) throws Exception {
        RfidCardDao rfidCardDao = SpringContext.INSTANCE.getBean(RfidCardDao.class);
        StringWriter stringWriter = new StringWriter();
        IOUtils.copy(RfidCardFix.class.getResourceAsStream("RfidCardFix.json"), stringWriter, "UTF-8");
        JSONObject jsonObject = new JSONObject(stringWriter.toString());
        writer.println(jsonObject);

        Set<String> serials = new HashSet<>();
        JSONArray jsonArray = jsonObject.getJSONArray("cards");
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
            String serial = jsonObject1.getString("sn");
            String number = jsonObject1.getString("cn");

            RfidCard rfidCard = rfidCardDao.findBySerialNumber(serial);
            if (rfidCard != null) {
                writer.println("ISTNIEJE" + serial + " == " + rfidCard);
                continue;
            }
            if (serials.contains(serial)) {
                writer.println("DUPLIKAT" + serial + " == " + rfidCard);
                continue;
            }
            serials.add(serial);

            rfidCard = new RfidCard();
            rfidCard.setRfidCardNature(RfidCardNature.Kindergarten);
            rfidCard.setRfidCardType(RfidCardType.Mifare1k);
            rfidCard.setCardNumber(number);
            rfidCard.setSerialNumber(serial);
            rfidCard.setRemarks("Import E-Dziecko PP14");
            rfidCard.setOverprint(6608497064017920L);
            rfidCardDao.save(rfidCard);
            writer.println("Zapisano:" + rfidCard);
        }
    }

    public static void addPrzedszkole2(PrintWriter writer) throws Exception {
        RfidCardDao rfidCardDao = SpringContext.INSTANCE.getBean(RfidCardDao.class);
        EstablishmentDao establishmentDao = SpringContext.INSTANCE.getBean(EstablishmentDao.class);
        HumanDao humanDao = SpringContext.INSTANCE.getBean(HumanDao.class);
        StringWriter stringWriter = new StringWriter();
        IOUtils.copy(RfidCardFix.class.getResourceAsStream("RfidCardFix.json"), stringWriter, "UTF-8");
        JSONObject jsonObject = new JSONObject(stringWriter.toString());
        writer.println(jsonObject);

        Establishment establishment = establishmentDao.byId(6592287857442816L);
        Set<String> serials = new HashSet<>();
        JSONArray jsonArray = jsonObject.getJSONArray("humans");
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
            String name = jsonObject1.getString("n");
            JSONArray jsonArray1 = jsonObject1.getJSONArray("cards");

            if (jsonArray1.length() == 0) {
                writer.println("PUSTY:" + name);
                continue;
            }

            Human human = new Human();
            human.setMonthlyRate(0);
            human.setName(name);
            humanDao.save(human);
            writer.println("Zapisano:" + human);
            establishment.addHuman(human);
            writer.println("Dodano osobę do podmiotu");

            for (int j = 0; j < jsonArray1.length(); j++) {
                String serial = jsonArray1.getString(j);
                RfidCard rfidCard = rfidCardDao.findBySerialNumber(serial);
                if (rfidCard == null) {
                    writer.println("NIE ISTNIEJE" + serial + " == " + rfidCard);
                    continue;
                }
                if (serials.contains(serial)) {
                    writer.println("DUPLIKAT" + serial + " == " + rfidCard);
                    continue;
                }
                serials.add(serial);
                rfidCard.setHuman(human);
                rfidCardDao.save(rfidCard);
            }
        }
        establishmentDao.save(establishment);
    }

    /**
     * Obsluga kart dodatkowych
     *
     * @param writer
     * @throws Exception
     */
    public static void addPrzedszkole3(PrintWriter writer) throws Exception {
        RfidCardDao rfidCardDao = SpringContext.INSTANCE.getBean(RfidCardDao.class);

        StringWriter stringWriter = new StringWriter();
        IOUtils.copy(RfidCardFix.class.getResourceAsStream("RfidCardFix.json"), stringWriter, "UTF-8");
        JSONObject jsonObject = new JSONObject(stringWriter.toString());
        writer.println(jsonObject);
        JSONArray jsonArray = jsonObject.getJSONArray("humans");
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
            JSONArray jsonArray1 = jsonObject1.getJSONArray("cards");
            String sn = jsonArray1.getString(0);
            RfidCard rfidCard = rfidCardDao.findBySerialNumber(sn);
            Human human = rfidCard.getHuman().safe();

            for (int j = 1; j < jsonArray1.length(); j++) {
                sn = jsonArray1.getString(j);
                rfidCard = rfidCardDao.findBySerialNumber(sn);
                if (rfidCard.getHumanChecked() != null) {
                    writer.println("ISTNIEJE JUŻ OSOBA DLA" + sn + " == " + human);
                } else {
                    rfidCard.setHuman(human);
                    rfidCardDao.save(rfidCard);
                    writer.println("PRZYPISANO " + human + " == " + rfidCard);
                }
            }
        }
    }

}
