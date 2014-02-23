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
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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

    static final long serialVersionUID = 7955832338496147669L;
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
    }

    @Override
    public String getFileName() {
        return fileName;
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
            Human human = event.obtainHuman();
            if (!rows.containsKey(human)) {
                rows.put(human, new DailyReportRow(human));
            }
            DailyReportRow row = rows.get(human);
            Date ed = event.getEventDate();
            if (ed.before(row.from.toDate())) {
                row.from = new DateTime(ed);
                row.fromEvent = event.getRfidEventType();
                row.fromEventId = event.getId();
            }
            if (ed.after(row.to.toDate())) {
                row.to = new DateTime(ed);
                row.toEvent = event.getRfidEventType();
                row.toEventId = event.getId();
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

    public static class DailyReportRow implements Serializable {

        static final long serialVersionUID = -449249150340878109L;
        private final Human human;
        private DateTime from = new DateTime(Long.MAX_VALUE);
        private DateTime to = new DateTime(0);
        private RfidEventType fromEvent = null;
        private RfidEventType toEvent = null;
        private long fromEventId = 0;
        private long toEventId = 0;

        private DailyReportRow(final Human human) {
            this.human = human;
        }

        public Human getHuman() {
            return human;
        }

        public DateTime getFrom() {
            return from;
        }

        public DateTime getTo() {
            return to;
        }

        public RfidEventType getFromEvent() {
            return fromEvent;
        }

        public RfidEventType getToEvent() {
            return toEvent;
        }

        public long getFromEventId() {
            return fromEventId;
        }

        public long getToEventId() {
            return toEventId;
        }

        public long duration() {
            return to.getMillis() - from.getMillis();
        }
    }

    public static final String RAPORT_DZIENNY = "Raport dzienny";
    private static final String LP = "Lp.";
    private static final String PRACOWNIK = "Pracownik";
    private static final String ZDARZENIE_OD = "Zdarzenie od";
    private static final String CZAS_OD = "Czas od";
    private static final String ZDARZENIE_DO = "Zdarzenie do";
    private static final String CZAS_DO = "Czas do";
    private static final String CZAS_RAZEM = "Czas pobytu";
    private static final String ID_OD = "GAE ID od";
    private static final String ID_DO = "GAE ID do";
    public static final String LISTA_NIEOBECNYCH = "Lista nieobecnych";

    @Override
    public Map<String, Container> asVaadinData() {
        final HashMap<String, Container> hashMap = new HashMap<>();
        final DateTimeFormatter mediumTime = AppSessionHelper.mediumTime(appSession);
        final PeriodFormatter hms = AppSessionHelper.hms();
        final Map<Human, DailyReportRow> reportRows = calcDailyReportRows();

        int rowNo = 0;
        IndexedContainer indexedContainer = new IndexedContainer();
        indexedContainer.addContainerProperty(LP, Integer.class, null);
        indexedContainer.addContainerProperty(PRACOWNIK, String.class, null);
        indexedContainer.addContainerProperty(ZDARZENIE_OD, String.class, null);
        indexedContainer.addContainerProperty(CZAS_OD, String.class, null);
        indexedContainer.addContainerProperty(ZDARZENIE_DO, String.class, null);
        indexedContainer.addContainerProperty(CZAS_DO, String.class, null);
        indexedContainer.addContainerProperty(CZAS_RAZEM, String.class, null);
        indexedContainer.addContainerProperty(ID_OD, Long.class, null);
        indexedContainer.addContainerProperty(ID_DO, Long.class, null);
        for (DailyReportRow row : reportRows.values()) {
            final Item item = indexedContainer.addItem(row);
            item.getItemProperty(LP).setValue(++rowNo);
            item.getItemProperty(PRACOWNIK).setValue(row.human.getName());
            item.getItemProperty(ZDARZENIE_OD).setValue(row.fromEvent.toString());
            item.getItemProperty(CZAS_OD).setValue(mediumTime.print(row.from));
            item.getItemProperty(ZDARZENIE_DO).setValue(row.toEvent.toString());
            item.getItemProperty(CZAS_DO).setValue(mediumTime.print(row.to));
            item.getItemProperty(CZAS_RAZEM).setValue(hms.print(new Period(row.duration())));
            item.getItemProperty(ID_OD).setValue(row.fromEventId);
            item.getItemProperty(ID_DO).setValue(row.toEventId);
        }
        hashMap.put(RAPORT_DZIENNY, indexedContainer);

        rowNo = 0;
        indexedContainer = new IndexedContainer();
        indexedContainer.addContainerProperty(LP, Integer.class, null);
        indexedContainer.addContainerProperty(PRACOWNIK, String.class, null);
        for (Human human : calcAbsences(reportRows)) {
            final Item item = indexedContainer.addItem(human);
            item.getItemProperty(LP).setValue(++rowNo);
            item.getItemProperty(PRACOWNIK).setValue(human.getName());
        }
        hashMap.put(LISTA_NIEOBECNYCH, indexedContainer);

        return hashMap;
    }

}
