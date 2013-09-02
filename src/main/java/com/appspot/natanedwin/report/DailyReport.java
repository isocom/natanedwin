package com.appspot.natanedwin.report;

import com.appspot.natanedwin.dao.RfidEventDao;
import com.appspot.natanedwin.entity.Human;
import com.appspot.natanedwin.entity.RfidCard;
import com.appspot.natanedwin.entity.RfidEvent;
import com.appspot.natanedwin.entity.UserAccountTools;
import com.appspot.natanedwin.service.appsession.AppSession;
import com.appspot.natanedwin.service.spring.SpringContext;
import com.pdfjet.Page;
import com.pdfjet.TextLine;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import jxl.write.Label;
import jxl.write.WritableSheet;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DailyReport implements Report {

    final private Date date;
    final private List<RfidEvent> events;
    final private String fileName;

    public DailyReport(final Date date) {
        this.date = date;

        RfidEventDao rfidEventDao = SpringContext.INSTANCE.getBean(RfidEventDao.class);
        events = rfidEventDao.findToday(date);
        fileName = "RPC Raport dzienny za " + date.getDate();
    }

    @Override
    public String getFileName() {
        return fileName;
    }

    @Override
    public String asHTML() {
        Collection<DailyReportRow> reportRows = calcDailyReportRows();
//        Ofy ofy = SpringContext.INSTANCE.getBean(Ofy.class);
        AppSession appSession = SpringContext.INSTANCE.getBean(AppSession.class);
        DateTimeFormatter mediumDateTime = DateTimeFormat.mediumDateTime();
        mediumDateTime = mediumDateTime.withZone(DateTimeZone.forID(appSession.getUserCredentials().getUserAccount().getDateTimeZone()));
        mediumDateTime = mediumDateTime.withLocale(UserAccountTools.getLocale(appSession.getUserCredentials().getUserAccount().getLocale()));

        StringBuilder sb = new StringBuilder();
        int rowNo = 0;
        sb.append("<body>");

        sb.append("<h1>Raport dzienny</h1>");
        sb.append("<h2>Raport za dzień: ").append(mediumDateTime.print(date.getTime())).append("</h2>");

        sb.append("<h3>Raport dzienny</h3>");
        sb.append("<table border=1>");
        sb.append("<tr>");
        sb.append("<th>Lp</th>");
        sb.append("<th>Pracownik</th>");
        sb.append("<th>Wejście</th>");
        sb.append("<th>Wyjście</th>");
        sb.append("<th>Czas Łączny</th>");
        sb.append("</tr>");
        for (DailyReportRow row : reportRows) {
            sb.append("<tr>");
            sb.append("<td>").append(++rowNo).append("</td>");
            sb.append("<td>").append(row.human.getName()).append("</td>");
            sb.append("<td>").append(row.from).append("</td>");
            sb.append("<td>").append(row.to).append("</td>");
            sb.append("<td>").append(row.duration() / 1000).append("</td>");
            sb.append("</tr>");
        }
        sb.append("</table>");
        sb.append("Ilość rekordów: ").append(reportRows.size()).append(".<br>");

        Set<Human> humans = new HashSet<>();

        sb.append("<h3>Zdarzenia na czytniku</h3>");
        sb.append("<ol>");
        for (RfidEvent rfidEvent : events) {
            sb.append("<li>");
//            Objectify o = ofy.ofy();
            String instant = mediumDateTime.print(rfidEvent.getEventDate().getTime());
            sb.append(instant).append(", ").append(rfidEvent.getRfidEventType()).append(", ");
            RfidCard rfidCard = rfidEvent.getRfidCard();
            sb.append(rfidCard.getCardNumber());
            sb.append(", ");
            sb.append(rfidCard.getHuman().get().getName());
            sb.append("</li>");
            humans.add(rfidCard.getHuman().get());
        }
        sb.append("</ol>");

        sb.append("<h3>Lista obecności</h3>");
        sb.append("<ol>");
        for (Human human : humans) {
            sb.append("<li>").append(human.getName()).append("</li>");
        }
        sb.append("</ol>");

        sb.append("</body>");
        return sb.toString();
    }

    @Override
    public ByteArrayStreamResource asPDF() {
        try {
            PDFReport pdfReport = new PDFReport();
            Page newPage = pdfReport.newPage();

            TextLine text = new TextLine(pdfReport.getFontHelvetica14());
            text.setText("Ala ma kota łąć");
            text.setPosition(100, 100);
            text.drawOn(newPage);

            return pdfReport.getReport();
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    @Override
    public ByteArrayStreamResource asXLS() {
        try {
            XLSReport xlsReport = new XLSReport();
            
            WritableSheet writableSheet = xlsReport.getWritableWorkbook().createSheet("Zdarzenia", 0);
            int row = 0, col = 0;
            writableSheet.addCell(new Label(col++, row, "LP"));
            writableSheet.addCell(new Label(col++, row, "Imię i Nazwisko"));
            writableSheet.addCell(new Label(col++, row, "Data i godzina"));
            writableSheet.addCell(new Label(col++, row, "Typ zdarzenia"));
            writableSheet.addCell(new Label(col++, row, "Karta"));
            row++;
            for (RfidEvent event : events) {
                col = 0;
                writableSheet.addCell(new Label(col++, row, "" + row));
                writableSheet.addCell(new Label(col++, row, event.getRfidCard().getHuman().get().getName()));
                writableSheet.addCell(new Label(col++, row, event.getEventDate().toString()));
                writableSheet.addCell(new Label(col++, row, event.getRfidEventType().toString()));
                writableSheet.addCell(new Label(col++, row, event.getRfidCard().getCardNumber()));
                row++;
            }
            
            return xlsReport.getReport();
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    private Collection<DailyReportRow> calcDailyReportRows() {
        HashMap<Human, DailyReportRow> rows = new HashMap<>();
        for (RfidEvent event : events) {
            Human human = event.getRfidCard().getHuman().get();
            if (!rows.containsKey(human)) {
                rows.put(human, new DailyReportRow(human));
            }
            DailyReportRow row = rows.get(human);
            Date ed = event.getEventDate();
            if (ed.before(row.from)) {
                row.from = ed;
            }
            if (ed.after(row.to)) {
                row.to = ed;
            }
        }
        return rows.values();
    }

    private class DailyReportRow {

        private final Human human;
        private Date from = new Date(Long.MAX_VALUE);
        private Date to = new Date(0);

        public DailyReportRow(final Human human) {
            this.human = human;
        }

        long duration() {
            return to.getTime() - from.getTime();
        }
    }
}
