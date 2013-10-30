package com.appspot.natanedwin.servlet.export;

import com.appspot.natanedwin.dao.HumanDao;
import com.appspot.natanedwin.dao.RfidCardDao;
import com.appspot.natanedwin.entity.Human;
import com.appspot.natanedwin.entity.RfidCard;
import com.appspot.natanedwin.service.ofy.Ofy;
import com.appspot.natanedwin.service.spring.SpringContext;
import com.googlecode.objectify.Key;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ExportServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            HumanDao humanDao = SpringContext.INSTANCE.getBean(HumanDao.class);
            List<Human> humans = humanDao.findAll();
            for (Human human : humans) {
                out.println(human.getName());
                out.println("\testablishment.getHumans().add(Ref.create(Key.create(Human.class," + human.getId() + ")));");
            }
        } catch (Exception e) {
            e.printStackTrace(out);
        } finally {
            out.close();
        }
    }

    protected void doGet1(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/xml;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            RfidCardDao rfidCardDao = SpringContext.INSTANCE.getBean(RfidCardDao.class);
            Ofy ofy = SpringContext.INSTANCE.getBean(Ofy.class);
            List<RfidCard> rfidCards = rfidCardDao.findAll();

            out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            out.println("<rfidCards>");
            for (RfidCard rfidCard : rfidCards) {
                out.print("<card ");
                out.print("id=\"" + rfidCard.getId() + "\" ");
                out.print("uuid=\"" + rfidCard.getUuid() + "\" ");
                Key<Human> humanKey = rfidCard.getHuman().getKey();
                Human human = new Human();
                human.setId(humanKey.getId());
                out.print("cardOwner=\"" + (humanKey != null ? ofy.ofy().load().entity(human).get().getName() : "--NIEPRZYPISANA--") + "\" ");
                out.print("cardNumber=\"" + rfidCard.getCardNumber() + "\" ");
                out.print("/>");
                out.println();
            }
            out.println("</rfidCards>");
        } catch (Exception e) {
            e.printStackTrace(out);
        } finally {
            out.close();
        }
    }
}
