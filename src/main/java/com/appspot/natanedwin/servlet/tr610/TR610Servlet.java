package com.appspot.natanedwin.servlet.tr610;

import com.appspot.natanedwin.dao.DeviceDao;
import com.appspot.natanedwin.entity.Device;
import com.appspot.natanedwin.entity.DeviceType;
import com.codahale.metrics.MetricRegistry;
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

    static final long serialVersionUID = 8412974669752828656L;
    @Autowired
    private transient DeviceDao deviceDao;
//    @Autowired
//    private transient MetricRegistry metricRegistry;
    @Autowired
    private transient CardDetected1 cardDetected1;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        metricRegistry.counter(MetricRegistry.name(TR610Servlet.class, "doGet", "call")).inc();
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
        resp.setCharacterEncoding("US-ASCII");
        PrintWriter writer = resp.getWriter();

        switch (command) {
            case CardDetected1:
                cardDetected1.cardDetected1(device, req, writer);
                break;
            case HeartBeat1:
                TR610Response.heartBeat1(writer, device);
                break;
            case Hello1:
                hello1(writer, device);
                break;
            case TTSoftCard:
                cardDetected1.cardTTSoft(req, writer, device);
                break;
        }

        writer.flush();
    }

    private void hello1(PrintWriter writer, Device device) {
        TR610Response.hello1(writer, device);

        try {
            JSONObject conf = device.getJsonConfiguration();
            final String k1 = "enableTTSoft";
            if (conf.has(k1) && conf.getBoolean(k1)) {
                TR610Response.enableLocalTTSoftDevices(writer);
            }
        } catch (JSONException jsone) {
        }
    }

}
