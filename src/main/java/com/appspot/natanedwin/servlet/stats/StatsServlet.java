package com.appspot.natanedwin.servlet.stats;

import com.appspot.natanedwin.dao.StatEntryDao;
import com.appspot.natanedwin.entity.StatEntry;
import com.appspot.natanedwin.service.spring.SpringContext;
import com.google.appengine.api.datastore.Text;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author prokob01
 */
public class StatsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StatEntryDao statEntryDao = SpringContext.INSTANCE.getBean(StatEntryDao.class);

        JSONObject jsonObject = new JSONObject();
        Map parameterMap = req.getParameterMap();
        for (Object key : parameterMap.keySet()) {
            String jsonKey = key.toString();
            String[] values = (String[]) parameterMap.get(key);
            StringBuilder jsonVal = new StringBuilder();
            for (int i = 0; i < values.length; i++) {
                jsonVal.append(values[i]);
                if (i < values.length - 1) {
                    jsonVal.append(", ");
                }
            }
            try {
                jsonObject.put(jsonKey, jsonVal.toString());
            } catch (JSONException jsone) {
            }
        }

        addHeaderValue(jsonObject, req, "X-AppEngine-Country");
        addHeaderValue(jsonObject, req, "X-AppEngine-Region");
        addHeaderValue(jsonObject, req, "X-AppEngine-City");
        addHeaderValue(jsonObject, req, "X-AppEngine-CityLatLong");
        addHeaderValue(jsonObject, req, "User-Agent");
        addHeaderValue(jsonObject, req, "Host");

        addValue(jsonObject, "remoteAddr", req.getRemoteAddr());
        addValue(jsonObject, "remoteHost", req.getRemoteHost());

        StatEntry statEntry = new StatEntry();
        statEntry.setJsonData(new Text(jsonObject.toString()));
        statEntryDao.save(statEntry);

        PrintWriter writer = resp.getWriter();
        writer.println("OK");
    }

    private static void addHeaderValue(JSONObject object, HttpServletRequest req, String key) {
        if (req.getHeader(key) != null) {
            try {
                object.put("header-" + key, req.getHeader(key));
            } catch (JSONException jsone) {
            }
        }
    }

    private static void addValue(JSONObject object, String key, String val) {
        if (val != null) {
            try {
                object.put(key, val);
            } catch (JSONException jsone) {
            }
        }
    }
}
