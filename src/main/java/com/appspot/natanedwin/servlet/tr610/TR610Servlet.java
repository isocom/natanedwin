package com.appspot.natanedwin.servlet.tr610;

import com.appspot.natanedwin.dao.DeviceDao;
import com.appspot.natanedwin.dao.RfidCardDao;
import com.appspot.natanedwin.dao.RfidEventDao;
import com.appspot.natanedwin.entity.Device;
import com.appspot.natanedwin.entity.DeviceType;
import com.appspot.natanedwin.entity.Human;
import com.appspot.natanedwin.entity.RfidCard;
import com.appspot.natanedwin.entity.RfidCardType;
import com.appspot.natanedwin.entity.RfidEvent;
import com.appspot.natanedwin.entity.RfidEventType;
import com.appspot.natanedwin.service.cardnumber.CardNumber;
import com.appspot.natanedwin.service.ofy.Ofy;
import com.appspot.natanedwin.service.spring.SpringContext;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author prokob01
 */
public class TR610Servlet extends HttpServlet {

    private static final String CMD_ESC = "~X";
    private static final String CMD_DEL = "|";
    private static final String CMD_END = CMD_DEL + "`";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Command command;
        String serialNumber;
        try {
            command = Command.valueOf(req.getParameter("cmd"));
            serialNumber = req.getParameter("sn");
        } catch (Exception e) {
            resp.sendError(500, e.toString());
            return;
        }
        DeviceDao deviceDao = SpringContext.INSTANCE.getBean(DeviceDao.class);

        Device device = deviceDao.findBySerialNumber(serialNumber);
        if (device == null) {
            device = new Device();
            device.setDeviceType(DeviceType.TR610);
            device.setSerialNumber(serialNumber);
            deviceDao.save(device);
        }

        resp.setContentType("text/plain");

        switch (command) {
            case CardDetected1:
                cardDetected1(resp.getWriter(), device, req.getParameter("cn"), RfidEventType.valueOf(req.getParameter("mode")));
                break;
            case HeartBeat1:
                heartBeat1(resp.getWriter(), device);
                break;
            case Hello1:
                hello1(resp.getWriter(), device);
                break;
        }
    }

    private void cardDetected1(PrintWriter resp, Device device, String serialNumber, RfidEventType mode) {
        RfidCardDao rfidCardDao = SpringContext.INSTANCE.getBean(RfidCardDao.class);
        RfidCard rfidCard = rfidCardDao.findBySerialNumber(serialNumber);
        if (rfidCard == null) {
            rfidCard = new RfidCard();
            rfidCard.setRfidCardType(RfidCardType.Mifare1k);
            rfidCard.setSerialNumber(serialNumber);
            CardNumber cardNumber = SpringContext.INSTANCE.getBean(CardNumber.class);
            rfidCard.setCardNumber(cardNumber.generate());
            rfidCardDao.save(rfidCard);
            display2(resp, "Dodano nowa karte", rfidCard.getCardNumber());
            return;
        }

        if (rfidCard.getHuman() == null) {
            display2(resp, "Karta nieprzypisana", rfidCard.getCardNumber());
            return;
        }

        RfidEventDao rfidEventDao = SpringContext.INSTANCE.getBean(RfidEventDao.class);
        RfidEvent rfidEvent = new RfidEvent();
        rfidEvent.setDevice(device);
        rfidEvent.setRfidCard(rfidCard);
        rfidEvent.setHuman(rfidCard.getHuman());
        rfidEvent.setRfidEventType(mode);
        rfidEventDao.save(rfidEvent);

        String line1 = "---";
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
        }
        String line2;
        try {
            Ofy ofy = SpringContext.INSTANCE.getBean(Ofy.class);
            Human human = new Human();
            human.setId(rfidCard.getHuman().getKey().getId());
            human = ofy.ofy().load().entity(human).now();
            line2 = human.getName();
        } catch (Throwable r) {
            line2 = r.getClass().getSimpleName();
        }
        display3(resp, line1, line2, rfidCard.getCardNumber());
    }

    private void heartBeat1(PrintWriter resp, Device device) {
        SimpleDateFormat dateFormater = new SimpleDateFormat("yyMMdd HHmmss");
        dateFormater.setTimeZone(TimeZone.getTimeZone("CET"));
        resp.print(CMD_ESC + "HeartBeat1" + CMD_DEL + dateFormater.format(new Date()) + CMD_END);
    }

    private void hello1(PrintWriter resp, Device device) {
        resp.print(CMD_ESC + "Hello1" + CMD_DEL + device.getId() + CMD_END);
    }

    private void display1(PrintWriter resp, String line1) {
        line1 = latinize(line1);
        resp.print(CMD_ESC + "Display1" + CMD_DEL + line1 + CMD_END);
    }

    private void display2(PrintWriter resp, String line1, String line2) {
        line1 = latinize(line1);
        line2 = latinize(line2);
        resp.print(CMD_ESC + "Display2" + CMD_DEL + line1 + CMD_DEL + line2 + CMD_END);
    }

    private void display3(PrintWriter resp, String line1, String line2, String line3) {
        line1 = latinize(line1);
        line2 = latinize(line2);
        line3 = latinize(line3);
        resp.print(CMD_ESC + "Display3" + CMD_DEL + line1 + CMD_DEL + line2 + CMD_DEL + line3 + CMD_END);
    }

    private void display4(PrintWriter resp, String line1, String line2, String line3, String line4) {
        line1 = latinize(line1);
        line2 = latinize(line2);
        line3 = latinize(line3);
        line4 = latinize(line4);
        resp.print(CMD_ESC + "Display4" + CMD_DEL + line1 + CMD_DEL + line2 + CMD_DEL + line3 + CMD_DEL + line4 + CMD_END);
    }

    private String latinize(String s) {
        StringBuilder sb = new StringBuilder();
        for (char c : s.toCharArray()) {
            switch (c) {
                case 'Ą':
                    c = 'A';
                    break;
                case 'ą':
                    c = 'a';
                    break;
                case 'Ć':
                    c = 'C';
                    break;
                case 'ć':
                    c = 'ć';
                    break;
                case 'Ę':
                    c = 'E';
                    break;
                case 'ę':
                    c = 'ę';
                    break;
                case 'Ł':
                    c = 'L';
                    break;
                case 'ł':
                    c = 'l';
                    break;
                case 'Ń':
                    c = 'N';
                    break;
                case 'ń':
                    c = 'n';
                    break;
                case 'Ó':
                    c = 'o';
                    break;
                case 'ó':
                    c = 'o';
                    break;
                case 'Ś':
                    c = 'S';
                    break;
                case 'ś':
                    c = 's';
                    break;
                case 'Ż':
                    c = 'Z';
                    break;
                case 'ż':
                    c = 'z';
                    break;
                case 'Ź':
                    c = 'z';
                    break;
                case 'ź':
                    c = 'z';
                    break;
            }
            sb.append(c);
        }
        return sb.toString();
    }
}
