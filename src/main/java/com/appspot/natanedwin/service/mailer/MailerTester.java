package com.appspot.natanedwin.service.mailer;

import com.appspot.natanedwin.service.spring.SpringContext;
import java.io.UnsupportedEncodingException;

/**
 *
 * @author prokob01
 */
public class MailerTester {

    public static void doTest() {
        Mailer mailer = SpringContext.INSTANCE.getBean(Mailer.class);
        mailer.send(produceTestMail());
    }

    private static Email produceTestMail() {
        Email mail = new Email();
        mail.addTo(new EmailAddress("Bart at GMAIL", "prokop.bart@gmail.com"));
        mail.addTo(new EmailAddress("Marek at ISOCOM", "marek.lachcik@isocom.eu"));
        mail.addTo(new EmailAddress("Grześ at ISOCOM", "grzegorz.cuprys@isocom.eu"));
        mail.setSubject("Text + HTML mail with inline pic and attachments - zażółć gęślą jaźń");
        mail.setTextBody("Mail czysty z polskimi znakami\nzażółć gęślą jaźń");
        mail.setHtmlBody("Mail <b>formatowany</b> <i>HTML</i> z obrazkiem w treści:<br>"
                + "<img src=\"http://natanedwin.appspot.com/images/logo.png\" /><br>"
                + "pchnąć w tę łódź jeża lub ośm skrzyń fig");
        try {
            mail.addAttachment(new ByteArrayAttachment("text/plain", "Tekst1.txt", "Ala ma kota 1\nĄĆĘŁÓ\n---".getBytes("UTF-8")));
            mail.addAttachment(new ByteArrayAttachment("text/plain", "Tekst2.txt", "Ala ma kota 2\nąćęłó\n---".getBytes("UTF-8")));
        } catch (UnsupportedEncodingException uee) {
            throw new RuntimeException(uee);
        }
        return mail;
    }
}
