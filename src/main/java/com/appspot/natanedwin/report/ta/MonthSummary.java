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
    public ByteArrayStreamResource asPDF() {
        throw new UnsupportedOperationException("Jeszcze nie gotowe."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ByteArrayStreamResource asXLS() {
        throw new UnsupportedOperationException("Jeszcze nie gotowe."); //To change body of generated methods, choose Tools | Templates.
    }

    private static final String LP = "Lp.";
    private static final String CZAS_OD = "Czas od";
    private static final String CZAS_DO = "Czas do";
    private static final String CZAS_RAZEM = "Czas pobytu";

    @Override
    public Map<String, Container> asVaadinData() {
        final HashMap<String, Container> hashMap = new HashMap<>();
        final DateTimeZone dateTimeZone = AppSessionHelper.dateTimeZone(appSession);
        final DateTimeFormatter mediumDateTime = AppSessionHelper.mediumDateTime(appSession);
        final DateTimeFormatter mediumTime = AppSessionHelper.mediumTime(appSession);
        final PeriodFormatter hms = AppSessionHelper.hms();

        int rowNo1 = 0;
        for (Human human : daysByHuman.keySet()) {
            final String key = ++rowNo1 + ". " + human.getName();
            final IndexedContainer indexedContainer = new IndexedContainer();
            indexedContainer.addContainerProperty(LP, Integer.class, null);
            indexedContainer.addContainerProperty(CZAS_OD, String.class, null);
            indexedContainer.addContainerProperty(CZAS_DO, String.class, null);
            indexedContainer.addContainerProperty(CZAS_RAZEM, String.class, null);

            int rowNo2 = 0;
            for (HumanDay hd : daysByHuman.get(human)) {
                final Item item = indexedContainer.addItem(hd);
                item.getItemProperty(LP).setValue(++rowNo2);
                DateTime from = new DateTime(hd.from, dateTimeZone);
                item.getItemProperty(CZAS_OD).setValue(mediumDateTime.print(from));
                DateTime to = new DateTime(hd.to, dateTimeZone);
                item.getItemProperty(CZAS_DO).setValue(mediumDateTime.print(to));
                Period period = new Period(hd.to.getTime() - hd.from.getTime());
                item.getItemProperty(CZAS_RAZEM).setValue(hms.print(period));
            }
            hashMap.put(key, indexedContainer);
        }
        return hashMap;

//        sb.append("<h2>Historia pracowników</h2>");
//        for (Human human : eventsByHuman.keySet()) {
//            sb.append("<p>").append(++rowNo1).append(". ").append(human.getName());
//            sb.append("<table border=1>");
//            sb.append("<tr>");
//            sb.append("<th>Lp</th>");
//            sb.append("<th>Zdarzenie</th>");
//            sb.append("<th>Czas zdarzenia</th>");
//            sb.append("<th>Użyta karta</th>");
//            sb.append("</tr>");
//            int rowNo2 = 0;
//            for (RfidEvent event : eventsByHuman.get(human)) {
//                sb.append("<tr>");
//                sb.append("<td>").append(++rowNo2).append("</td>");
//                sb.append("<td>").append(event.getRfidEventType()).append("</td>");
//                sb.append("<td>").append(event.getEventDate()).append("</td>");
//                sb.append("<td>").append(event.obtainRfidCard() != null ? event.obtainRfidCard().getCardNumber() : "- brak karty -").append("</td>");
//                sb.append("</tr>");
//            }
//            sb.append("</table>");
//            sb.append("</p>\n");
//        }
    }

    class HumanDay implements Serializable {

        Date from;
        Date to;
    }

}
