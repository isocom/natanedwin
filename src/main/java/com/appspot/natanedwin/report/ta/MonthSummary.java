package com.appspot.natanedwin.report.ta;

import com.appspot.natanedwin.dao.EstablishmentDao;
import com.appspot.natanedwin.dao.RfidEventDao;
import com.appspot.natanedwin.entity.Establishment;
import com.appspot.natanedwin.entity.Human;
import com.appspot.natanedwin.entity.RfidEvent;
import com.appspot.natanedwin.entity.RfidEventType;
import com.appspot.natanedwin.report.ByteArrayStreamResource;
import com.appspot.natanedwin.report.Report;
import com.appspot.natanedwin.service.appsession.AppSession;
import com.appspot.natanedwin.service.appsession.AppSessionHelper;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.PeriodFormatter;
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
    private final Map<Human, List<RfidEvent>> eventsByHuman = new HashMap<>();
    private final Map<Human, List<HumanDay>> daysByHuman = new HashMap<>();

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
        for (RfidEvent event : events) {
            Human human = event.safeHuman();
            if (!eventsByHuman.containsKey(human)) {
                eventsByHuman.put(human, new ArrayList<RfidEvent>());
            }
            eventsByHuman.get(human).add(event);
        }

        for (Human human : eventsByHuman.keySet()) {
            HumanDay humanDay = null;
            for (RfidEvent event : eventsByHuman.get(human)) {
                if (humanDay == null && event.getRfidEventType() == RfidEventType.In) {
                    humanDay = new HumanDay();
                    humanDay.from = event.getEventDate();
                }
                if (humanDay != null && event.getRfidEventType() == RfidEventType.Out) {
                    humanDay.to = event.getEventDate();
                    if (!daysByHuman.containsKey(human)) {
                        daysByHuman.put(human, new ArrayList<HumanDay>());
                    }
                    List<HumanDay> humanDays = daysByHuman.get(human);
                    humanDays.add(humanDay);
                    humanDay = null;
                }
            }
        }
    }

    @Override
    public String getFileName() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String asHTML() {
        DateTimeZone dateTimeZone = AppSessionHelper.dateTimeZone(appSession);
        DateTimeFormatter mediumDateTime = AppSessionHelper.mediumDateTime(appSession);
        PeriodFormatter hms = AppSessionHelper.hms();
        StringBuilder sb = new StringBuilder();

        sb.append("<body>");
        sb.append("<h1>Raport miesięczny</h1>");
        sb.append("<h2>Lista obecności</h2>");
        int rowNo1 = 0;
        for (Human human : daysByHuman.keySet()) {
            sb.append("<p>").append(++rowNo1).append(". ").append(human.getName());
            sb.append("<table border=1>");
            sb.append("<tr>");
            sb.append("<th>Lp</th>");
            sb.append("<th>Od</th>");
            sb.append("<th>Do</th>");
            sb.append("<th>Czas</th>");
            sb.append("</tr>");
            int rowNo2 = 0;
            for (HumanDay hd : daysByHuman.get(human)) {
                sb.append("<tr>");
                sb.append("<td>").append(++rowNo2).append("</td>");
                DateTime from = new DateTime(hd.from, dateTimeZone);
                sb.append("<td>").append(mediumDateTime.print(from)).append("</td>");
                DateTime to = new DateTime(hd.to, dateTimeZone);
                sb.append("<td>").append(mediumDateTime.print(to)).append("</td>");
                Period period = new Period(hd.to.getTime() - hd.from.getTime());
                sb.append("<td>").append(hms.print(period)).append("</td>");
                sb.append("</tr>");
            }
            sb.append("</table>");
            sb.append("</p>\n");
        }
        sb.append("<h2>Historia pracowników</h2>");
        rowNo1 = 0;
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
                sb.append("<td>").append(event.obtainRfidCard() != null ? event.obtainRfidCard().getCardNumber() : "- brak karty -").append("</td>");
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

    class HumanDay {

        Date from;
        Date to;
    }

}
