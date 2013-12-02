package com.appspot.natanedwin.report.ta;

import com.appspot.natanedwin.dao.EstablishmentDao;
import com.appspot.natanedwin.dao.RfidEventDao;
import com.appspot.natanedwin.entity.Establishment;
import com.appspot.natanedwin.entity.Human;
import com.appspot.natanedwin.entity.RfidEvent;
import com.appspot.natanedwin.entity.RfidEventType;
import com.appspot.natanedwin.report.ByteArrayStreamResource;
import com.appspot.natanedwin.report.DummyPDFReport;
import com.appspot.natanedwin.report.Report;
import com.appspot.natanedwin.service.appsession.AppSession;
import com.appspot.natanedwin.service.appsession.AppSessionHelper;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.PeriodFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

@Configurable(preConstruction = true)
public class DayStatus implements Report {

    final private DateTime date;
    final private List<RfidEvent> events;
    final private List<Human> humans;
    final private String fileName;

    @Autowired
    private transient AppSession appSession;
    @Autowired
    private transient EstablishmentDao establishmentDao;
    @Autowired
    private transient RfidEventDao rfidEventDao;

    public DayStatus(final Date date) {
        this.date = new DateTime(date, AppSessionHelper.dateTimeZone(appSession));

        long establishmentId = AppSessionHelper.establishmentId(appSession);
        Establishment establishment = establishmentDao.byId(establishmentId);
        humans = establishment.safeHumans();

        events = rfidEventDao.find(this.date, humans);
        DateTimeFormatter mediumDate = AppSessionHelper.mediumDate(appSession);
        fileName = "RPC Stan dnia " + mediumDate.print(this.date);

        Iterator<RfidEvent> i = events.iterator();
        while (i.hasNext()) {
            Human human = i.next().safeRfidCard().getHuman().safe();
            if (human == null) {
                continue;
            }
            if (!humans.contains(human)) {
                i.remove();
            }
        }
    }

    @Override
    public String getFileName() {
        return fileName;
    }

    @Override
    public String asHTML() {
        DateTimeFormatter mediumTime = AppSessionHelper.mediumTime(appSession);
        PeriodFormatter hms = AppSessionHelper.hms();

        Map<Human, DailyReportRow> reportRows = calcDailyReportRows();
        StringBuilder sb = new StringBuilder();
        int rowNo = 0;
        sb.append("<body>");

        sb.append("<h1>Raport dzienny</h1>");
        sb.append("<h2>").append(fileName).append("</h2>");

        sb.append("<h3>Raport dzienny</h3>");
        sb.append("<table border=1>");
        sb.append("<tr>");
        sb.append("<th>Lp</th>");
        sb.append("<th>Pracownik</th>");
        sb.append("<th>Zdarzenie od</th>");
        sb.append("<th>Czas od</th>");
        sb.append("<th>Zdarzenie do</th>");
        sb.append("<th>Czas do</th>");
        sb.append("<th>Czas łączny</th>");
        sb.append("</tr>");
        for (DailyReportRow row : reportRows.values()) {
            sb.append("<tr>");
            sb.append("<td>").append(++rowNo).append("</td>");
            sb.append("<td>").append(row.human.getName()).append("</td>");
            sb.append("<td>").append(row.fromEvent).append("</td>");
            sb.append("<td>").append(mediumTime.print(row.from)).append("</td>");
            sb.append("<td>").append(row.toEvent).append("</td>");
            sb.append("<td>").append(mediumTime.print(row.to)).append("</td>");
            sb.append("<td>").append(hms.print(new Period(row.duration()))).append("</td>");
            sb.append("</tr>");
        }
        sb.append("</table>");
        sb.append("Ilość rekordów: ").append(reportRows.size()).append(".<br>");

        sb.append("<h3>Lista nieobecnych</h3>");
        sb.append("<ol>");
        for (Human human : calcAbsences(reportRows)) {
            sb.append("<li>").append(human.getName()).append("</li>");
        }
        sb.append("</ol>");

        sb.append("</body>");
        return sb.toString();
    }

    @Override
    public ByteArrayStreamResource asPDF() {
        return DummyPDFReport.asPDF();
    }

    @Override
    public ByteArrayStreamResource asXLS() {
        return DayStatusXLS.asXLS(this);
    }

    Map<Human, DailyReportRow> calcDailyReportRows() {
        HashMap<Human, DailyReportRow> rows = new HashMap<>();
        for (RfidEvent event : events) {
            Human human = event.safeRfidCard().getHuman().get();
            if (!rows.containsKey(human)) {
                rows.put(human, new DailyReportRow(human));
            }
            DailyReportRow row = rows.get(human);
            Date ed = event.getEventDate();
            if (ed.before(row.from.toDate())) {
                row.from = new DateTime(ed);
                row.fromEvent = event.getRfidEventType();
            }
            if (ed.after(row.to.toDate())) {
                row.to = new DateTime(ed);
                row.toEvent = event.getRfidEventType();
            }
        }
        return rows;
    }

    List<Human> calcAbsences(Map<Human, DailyReportRow> reportRows) {
        List<Human> absences = new ArrayList<>();
        for (Human human : humans) {
            if (reportRows.containsKey(human)) {
                continue;
            }
            absences.add(human);
        }
        return absences;
    }

    public List<RfidEvent> getEvents() {
        return events;
    }

    class DailyReportRow {

        final Human human;
        DateTime from = new DateTime(Long.MAX_VALUE);
        DateTime to = new DateTime(0);
        RfidEventType fromEvent = null;
        RfidEventType toEvent = null;

        private DailyReportRow(final Human human) {
            this.human = human;
        }

        long duration() {
            return to.getMillis() - from.getMillis();
        }
    }
}
