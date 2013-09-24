package com.appspot.natanedwin.report.ta;

import com.appspot.natanedwin.dao.RfidEventDao;
import com.appspot.natanedwin.entity.Human;
import com.appspot.natanedwin.entity.RfidCard;
import com.appspot.natanedwin.entity.RfidEvent;
import com.appspot.natanedwin.report.ByteArrayStreamResource;
import com.appspot.natanedwin.report.PDFReport;
import com.appspot.natanedwin.report.Report;
import com.appspot.natanedwin.report.XLSReport;
import com.appspot.natanedwin.service.appsession.Formatters;
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
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.PeriodFormatter;

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
        DateTimeFormatter mediumDateTime = Formatters.getMediumDateTime();
        DateTimeFormatter mediumTime = Formatters.getMediumTime();
        PeriodFormatter hms = Formatters.getHMS();

        Collection<DailyReportRow> reportRows = calcDailyReportRows();
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
            sb.append("<td>").append(mediumTime.print(row.from.getTime())).append("</td>");
            sb.append("<td>").append(mediumTime.print(row.to.getTime())).append("</td>");
            sb.append("<td>").append(hms.print(new Period(row.to.getTime() - row.from.getTime()))).append("</td>");
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
            Collection<DailyReportRow> reportRows = calcDailyReportRows();
            XLSReport xlsReport = new XLSReport();

            WritableSheet writableSheet = xlsReport.getWritableWorkbook().createSheet("Zdarzenia", 0);
            int r = 0, c = 0;
            writableSheet.addCell(new Label(c++, r, "LP"));
            writableSheet.addCell(new Label(c++, r, "Imię i Nazwisko"));
            writableSheet.addCell(new Label(c++, r, "Data i godzina"));
            writableSheet.addCell(new Label(c++, r, "Typ zdarzenia"));
            writableSheet.addCell(new Label(c++, r, "Karta"));
            r++;
            for (RfidEvent event : events) {
                c = 0;
                writableSheet.addCell(new Label(c++, r, "" + r));
                writableSheet.addCell(new Label(c++, r, event.getRfidCard().getHuman().get().getName()));
                writableSheet.addCell(new Label(c++, r, event.getEventDate().toString()));
                writableSheet.addCell(new Label(c++, r, event.getRfidEventType().toString()));
                writableSheet.addCell(new Label(c++, r, event.getRfidCard().getCardNumber()));
                r++;
            }

            writableSheet = xlsReport.getWritableWorkbook().createSheet("Zdarzenia", 0);
            r = 0;
            c = 0;
            writableSheet.addCell(new Label(c++, r, "LP"));
            writableSheet.addCell(new Label(c++, r, "Imię i Nazwisko"));
            writableSheet.addCell(new Label(c++, r, "Wejście"));
            writableSheet.addCell(new Label(c++, r, "Wyjście"));
            writableSheet.addCell(new Label(c++, r, "Czas Łączny"));
            for (DailyReportRow row : reportRows) {
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
