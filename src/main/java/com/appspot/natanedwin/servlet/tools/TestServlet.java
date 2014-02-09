package com.appspot.natanedwin.servlet.tools;

import com.appspot.natanedwin.dao.RfidCardDao;
import com.appspot.natanedwin.entity.RfidCard;
import com.appspot.natanedwin.entity.RfidCardType;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

@Configurable
public class TestServlet extends HttpServlet {

    @Autowired
    RfidCardDao rfidCardDao;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain");
        PrintWriter writer = resp.getWriter();
        writer.println("Test servlet");

        try {
            List<RfidCard> rfidCards = rfidCardDao.findRecent(30);
            rfidCards = new ArrayList<>(rfidCards);
            Collections.sort(rfidCards, new Comparator<RfidCard>() {

                @Override
                public int compare(RfidCard o1, RfidCard o2) {
                    return -o1.getFirstTimeSeen().compareTo(o2.getFirstTimeSeen());
                }
            });
            writer.println("1");
            for (RfidCard rfidCard : rfidCards) {
                writer.println(rfidCard.getFirstTimeSeen() + " " + rfidCard.getRfidCardType());
            }
            Iterator<RfidCard> iterator = rfidCards.iterator();
            while (iterator.hasNext()) {
                RfidCard next = iterator.next();
                if (next.getRfidCardType() != RfidCardType.Vacant) {
                    iterator.remove();
                }
            }

            writer.println("2");
            for (RfidCard rfidCard : rfidCards) {
                writer.println(rfidCard.getFirstTimeSeen() + " " + rfidCard.getRfidCardType());
            }

            if (rfidCards.isEmpty()) {
                writer.println("No vacant card to assign");
                return;
            }
        } catch (Exception e) {
            e.printStackTrace(writer);
        }
    }

}
