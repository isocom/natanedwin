package com.appspot.natanedwin.servlet.tools;

import com.appspot.natanedwin.dao.EstablishmentDao;
import com.appspot.natanedwin.entity.Establishment;
import com.appspot.natanedwin.entity.Human;
import com.appspot.natanedwin.service.mailer.Mailer;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

@Configurable
public class PeriodicInvoiceServlet extends HttpServlet {

    @Autowired
    private EstablishmentDao establishmentDao;
    @Autowired
    private Mailer mailer;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StringBuilder sb = new StringBuilder();
        long total = 0;

        sb.append("Miesięczne rozliczenie kart RFID u klientów\n\n");
        sb.append("----------------------------------------------------\n");
        List<Establishment> establishments = establishmentDao.findAll();
        for (Establishment establishment : establishments) {
            long subTotal = 0;
            sb.append("Firma: ").append(establishment.getName()).append('\n');
            List<Human> humans = establishment.safeHumans();
            for (Human human : humans) {
                sb.append(human.getName()).append(" opłata ").append(human.getMonthlyRate() / 100.0).append(" zł.\n");
                subTotal += human.getMonthlyRate();
            }
            sb.append("Razem firma: ").append(subTotal / 100.0).append(" zł.\n");
            sb.append("Połowa: ").append(subTotal / 200.0).append(" zł.\n");
            sb.append("Ilość kart: ").append(humans.size()).append(" szt.\n");
            sb.append("Opis FV: Dostęp do aplikacji churowej RFID dla: ").append(establishment.getName()).append(".\n");
            sb.append("----------------------------------------------------\n");
            total += subTotal;
        }
        sb.append("RAZEM wszystkie firmy: ").append(total / 100.0).append(" zł.\n");
        sb.append("Wygenerowano automatycznie: ").append(new Date()).append(" (pierwszy poniedziałek miesiąca).\n");

        resp.setContentType("text/plain;charset=UTF-8");
        resp.getWriter().print(sb);

        mailer.sendToAdmins("Miesięczna nota obciążeniowa", sb.toString());
    }

}
