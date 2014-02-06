package com.appspot.natanedwin.servlet.tools;

import com.appspot.natanedwin.dao.EstablishmentDao;
import com.appspot.natanedwin.entity.Establishment;
import com.appspot.natanedwin.entity.Human;
import com.appspot.natanedwin.service.mailer.Email;
import com.appspot.natanedwin.service.mailer.EmailAddress;
import com.appspot.natanedwin.service.mailer.Mailer;
import com.appspot.natanedwin.service.mailer.TextFileAttachment;
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
        sb.append("----------------------------------------------------\n\n");
        List<Establishment> establishments = establishmentDao.findAll();
        for (Establishment establishment : establishments) {
            long subTotal = 0;
            int active = 0;
            sb.append("Firma: ").append(establishment.getName()).append('\n');
            List<Human> humans = establishment.safeHumans();
            for (Human human : humans) {
                if (human.isActive()) {
                    subTotal += human.getMonthlyRate();
                    sb.append(++active).append(". ");
                } else {
                    sb.append("NIEAKTYWNA - ");
                }
                sb.append(human.getName()).append(" opłata ").append(human.getMonthlyRate() / 100.0).append(" zł.\n");
            }
            sb.append("Razem firma: ").append(subTotal / 100.0).append(" zł.\n");
            sb.append("Połowa: ").append(subTotal / 200.0).append(" zł.\n");
            sb.append("Ilość osób: ").append(humans.size()).append(".\n");
            sb.append("Ilość aktywnych osób: ").append(active).append(".\n");
            sb.append("Opis FV: Dostęp do aplikacji chmurowej RFID dla: ").append(establishment.getName()).append(".\n");
            sb.append("----------------------------------------------------\n\n");
            total += subTotal;
        }
        sb.append("RAZEM wszystkie firmy: ").append(total / 100.0).append(" zł.\n");
        sb.append("Wygenerowano automatycznie: ").append(new Date()).append(" (pierwszy poniedziałek miesiąca).\n");

        resp.setContentType("text/plain");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().print(sb);

        Email email = new Email();
        email.addTo(new EmailAddress("Karolina", "karolina.wysocka.prokop@gmail.com"));
        email.addCc(new EmailAddress("ISOCOM", "edziecko@isocom.eu"));
        email.addBcc(new EmailAddress("JA SAM", "prokop.bart@gmail.com"));
        email.setSubject("test pl znaki ... Miesięczna podstawa fakturowania");
        email.setTextBody("Patrz załącznik.");
        email.addAttachment(new TextFileAttachment("rozliczenie.txt", sb.toString()));
        mailer.send(email);
    }

}
