package com.appspot.natanedwin.servlet.tools;

import com.appspot.natanedwin.dao.RfidCardDao;
import com.appspot.natanedwin.entity.Human;
import com.appspot.natanedwin.entity.RfidCard;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

@Configurable
public class TestServlet extends HttpServlet {

    static final long serialVersionUID = -6910611062558782002L;
    @Autowired
    private transient RfidCardDao rfidCardDao;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain");
        PrintWriter writer = resp.getWriter();
        writer.println("Test servlet");

        try {
            String cn = req.getParameter("cn");

            RfidCard rfidCard = rfidCardDao.findByCardNumber(cn);
            Human human = rfidCard.getHumanChecked();
            List<RfidCard> rfidCards = rfidCardDao.findByHuman(human);
            writer.println("1");
            StringBuilder sb = new StringBuilder();
            for (RfidCard card : rfidCards) {
                writer.println(card.getFirstTimeSeen() + " " + card.getRfidCardType() + " / " + card.getCardNumber());
                sb.append(card.getCardNumber()).append(",");
            }
            writer.println("2 - " + sb);
        } catch (Exception e) {
            e.printStackTrace(writer);
        }
    }

}
