package com.appspot.natanedwin.report.ta;

import com.appspot.natanedwin.entity.Human;
import com.appspot.natanedwin.entity.RfidEvent;
import com.appspot.natanedwin.report.ByteArrayStreamResource;
import com.appspot.natanedwin.report.XLSReport;
import com.appspot.natanedwin.service.appsession.AppSession;
import com.appspot.natanedwin.service.appsession.AppSessionHelper;
import static com.appspot.natanedwin.service.appsession.AppSessionHelper.hms;
import com.appspot.natanedwin.service.spring.SpringContext;
import java.util.Map;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WriteException;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormatter;

public final class DayStatusXLS {

    public static ByteArrayStreamResource asXLS(DayStatus dayStatus) {
        AppSession appSession = SpringContext.INSTANCE.getBean(AppSession.class);
        DateTimeFormatter mediumTime = AppSessionHelper.mediumTime(appSession);
        try {
            Map<Human, DayStatus.DailyReportRow> reportRows = dayStatus.calcDailyReportRows();
            XLSReport xlsReport = new XLSReport();

            WritableSheet writableSheet = xlsReport.getWritableWorkbook().createSheet("Zdarzenia", 0);
            int r = 0, c = 0;
            writableSheet.addCell(new Label(c++, r, "LP"));
            writableSheet.addCell(new Label(c++, r, "Imię i Nazwisko"));
            writableSheet.addCell(new Label(c++, r, "Data i godzina"));
            writableSheet.addCell(new Label(c++, r, "Typ zdarzenia"));
            writableSheet.addCell(new Label(c++, r, "Karta"));
            r++;
            for (RfidEvent event : dayStatus.getEvents()) {
                c = 0;
                writableSheet.addCell(new Label(c++, r, "" + r));
                writableSheet.addCell(new Label(c++, r, event.obtainHuman().getName()));
                writableSheet.addCell(new Label(c++, r, event.getEventDate().toString()));
                writableSheet.addCell(new Label(c++, r, event.getRfidEventType().name()));
                writableSheet.addCell(new Label(c++, r, event.obtainRfidCard() != null ? event.obtainRfidCard().getCardNumber() : "- brak karty -"));
                r++;
            }

            writableSheet = xlsReport.getWritableWorkbook().createSheet("Status Dnia", 1);
            r = 0;
            c = 0;
            writableSheet.addCell(new Label(c++, r, "LP"));
            writableSheet.addCell(new Label(c++, r, "Imię i Nazwisko"));
            writableSheet.addCell(new Label(c++, r, "Zdarzenie od"));
            writableSheet.addCell(new Label(c++, r, "Czas od"));
            writableSheet.addCell(new Label(c++, r, "Zdarzenie do"));
            writableSheet.addCell(new Label(c++, r, "Czas do"));
            writableSheet.addCell(new Label(c++, r, "Czas łączny"));
            for (DayStatus.DailyReportRow row : reportRows.values()) {
                c = 0;
                writableSheet.addCell(new Label(c++, r, "" + r));
                writableSheet.addCell(new Label(c++, r, row.getHuman().getName()));
                writableSheet.addCell(new Label(c++, r, row.getFromEvent().toString()));
                writableSheet.addCell(new Label(c++, r, mediumTime.print(row.getFrom())));
                writableSheet.addCell(new Label(c++, r, row.getToEvent().toString()));
                writableSheet.addCell(new Label(c++, r, mediumTime.print(row.getTo())));
                writableSheet.addCell(new Label(c++, r, hms().print(new Period(row.duration()))));
                r++;
            }

            writableSheet = xlsReport.getWritableWorkbook().createSheet("Lista nieobecnych", 2);
            r = 0;
            c = 0;
            writableSheet.addCell(new Label(c++, r, "LP"));
            writableSheet.addCell(new Label(c++, r, "Imię i Nazwisko"));
            for (Human human : dayStatus.calcAbsences(reportRows)) {
                c = 0;
                writableSheet.addCell(new Label(c++, r, "" + r));
                writableSheet.addCell(new Label(c++, r, human.getName()));
                r++;
            }
            return xlsReport.getReport();
        } catch (WriteException t) {
            throw new RuntimeException(t);
        }
    }

}
