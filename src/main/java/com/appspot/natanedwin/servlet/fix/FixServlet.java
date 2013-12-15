package com.appspot.natanedwin.servlet.fix;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FixServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/plain");
        final PrintWriter writer = resp.getWriter();
        if (!"TTSoft".equals(req.getParameter("password"))) {
            writer.println("Niepoprawne hasło");
            return;
        }
        
//        HumanFix.fixReSave(writer);
//        RfidCardFix.fixCardOverprint(writer);
        DeviceFix.fixReSave(writer);
//        EstablishmentFix.fixResDruk1(writer);
//        UserAccountFix.fixRefEstablishment(writer);
//        UserAccountFix.fixReSave(writer);
//        RfidEventFix.fixCardIndexing(writer);
    }
}
