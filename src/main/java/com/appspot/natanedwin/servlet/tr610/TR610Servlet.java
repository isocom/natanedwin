package com.appspot.natanedwin.servlet.tr610;

import com.appspot.natanedwin.dao.DeviceDao;
import com.appspot.natanedwin.dao.HumanDao;
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
import com.google.common.base.Strings;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

/**
 *
 * @author prokob01
 */
@Configurable
public class TR610Servlet extends HttpServlet {

    @Autowired
    private CardNumber cardNumber;
    @Autowired
    private DeviceDao deviceDao;
    @Autowired
    private HumanDao humanDao;
    @Autowired
    private RfidCardDao rfidCardDao;
    @Autowired
    private RfidEventDao rfidEventDao;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (Strings.isNullOrEmpty(req.getParameter("cmd"))) {
            resp.sendError(500, "F1");
            return;
        }
        if (Strings.isNullOrEmpty(req.getParameter("sn"))) {
            resp.sendError(500, "F2");
            return;
        }

        Command command;
        String serialNumber;
        try {
            command = Command.valueOf(req.getParameter("cmd"));
            serialNumber = req.getParameter("sn");
        } catch (Exception e) {
            resp.sendError(500, e.toString());
            return;
        }

        Device device = deviceDao.findBySerialNumber(serialNumber);
        if (device == null) {
            device = new Device();
            device.setDeviceType(DeviceType.TR610);
            device.setSerialNumber(serialNumber);
            deviceDao.save(device);
        }

        resp.setContentType("text/plain");
        PrintWriter writer = resp.getWriter();

        switch (command) {
            case CardDetected1:
                cardDetected1(writer, device, req.getParameter("cn"), RfidEventType.valueOf(req.getParameter("mode")));
                break;
            case HeartBeat1:
                TR610Response.heartBeat1(writer, device);
                break;
            case Hello1:
                hello1(writer, device);
                break;
        }

        writer.flush();
    }

    private void hello1(PrintWriter writer, Device device) {
        TR610Response.hello1(writer, device);

        try {
            JSONObject conf = device.getJsonConfiguration();
            final String k1 = "enableTTSoft";
            if (conf.has(k1) && "true".equalsIgnoreCase(conf.getString(k1))) {
                TR610Response.enableLocalTTSoftDevices(writer);
            }
        } catch (JSONException jsone) {
        }
    }

    private void cardDetected1(PrintWriter resp, Device device, String serialNumber, RfidEventType mode) {
        RfidCard rfidCard = rfidCardDao.findBySerialNumber(serialNumber);
        if (rfidCard == null) {
            rfidCard = new RfidCard();
            rfidCard.setRfidCardType(RfidCardType.Mifare1k);
            rfidCard.setSerialNumber(serialNumber);
            rfidCard.setCardNumber(cardNumber.generate());
            rfidCardDao.save(rfidCard);
            TR610Response.display2(resp, "Dodano nowa karte", rfidCard.getCardNumber());
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

}
