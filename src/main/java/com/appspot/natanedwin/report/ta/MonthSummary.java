package com.appspot.natanedwin.report.ta;

import com.appspot.natanedwin.dao.EstablishmentDao;
import com.appspot.natanedwin.dao.RfidEventDao;
import com.appspot.natanedwin.entity.Establishment;
import com.appspot.natanedwin.entity.Human;
import com.appspot.natanedwin.entity.RfidEvent;
import com.appspot.natanedwin.report.ByteArrayStreamResource;
import com.appspot.natanedwin.report.Report;
import com.appspot.natanedwin.service.appsession.AppSession;
import com.appspot.natanedwin.service.appsession.AppSessionHelper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

@Configurable(preConstruction = true)
public class MonthSummary implements Report {

    @Autowired
    private transient AppSession appSession;
    @Autowired
    private transient EstablishmentDao establishmentDao;
    @Autowired
    private transient RfidEventDao rfidEventDao;
    private Map<Human, List<RfidEvent>> eventsByHuman;

    public MonthSummary(int year, int month) {
        DateTimeZone dateTimeZone = AppSessionHelper.dateTimeZone(appSession);
        DateTime from = new DateTime(year, month, 1, 0, 0, dateTimeZone);
        DateTime to = from.dayOfMonth().withMaximumValue().millisOfDay().withMaximumValue();
        long establishmentId = AppSessionHelper.establishmentId(appSession);
        Establishment establishment = establishmentDao.byId(establishmentId);
        List<Human> humans = establishment.safeHumans();
        List<RfidEvent> events = rfidEventDao.find(from, to, humans);
        calc(events);
    }

    private void calc(List<RfidEvent> events) {
        // 1. agregate by human
        eventsByHuman = new HashMap<>();
        for (RfidEvent event : events) {
            Human human = event.safeHuman();
            if (!eventsByHuman.containsKey(human)) {
                eventsByHuman.put(human, new ArrayList<RfidEvent>());
            }
            eventsByHuman.get(human).add(event);
        }
    }

    @Override
    public String getFileName() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String asHTML() {
        StringBuilder sb = new StringBuilder();
        sb.append("<body>");
        sb.append("<h1>Raport miesięczny</h1>");
        sb.append("<h2>Grafik obecności</h2>");
        sb.append("<h2>Historia pracowników</h2>");
        int rowNo1 = 0;
        for (Human human : eventsByHuman.keySet()) {
            sb.append("<p>").append(++rowNo1).append(". ").append(human.getName());
            sb.append("<table border=1>");
            sb.append("<tr>");
            sb.append("<th>Lp</th>");
            sb.append("<th>Zdarzenie</th>");
            sb.append("<th>Czas zdarzenia</th>");
            sb.append("<th>Użyta karta</th>");
            sb.append("</tr>");
            int rowNo2 = 0;
            for (RfidEvent event : eventsByHuman.get(human)) {
                sb.append("<tr>");
                sb.append("<td>").append(++rowNo2).append("</td>");
                sb.append("<td>").append(event.getRfidEventType()).append("</td>");
                sb.append("<td>").append(event.getEventDate()).append("</td>");
                sb.append("<td>").append(event.safeRfidCard().getCardNumber()).append("</td>");
                sb.append("</tr>");
            }
            sb.append("</table>");
            sb.append("</p>\n");
        }
        sb.append("</body>");
        return sb.toString();
    }

    @Override
    public ByteArrayStreamResource asPDF() {
        throw new UnsupportedOperationException("Jeszcze nie gotowe."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ByteArrayStreamResource asXLS() {
        throw new UnsupportedOperationException("Jeszcze nie gotowe."); //To change body of generated methods, choose Tools | Templates.
    }

}
