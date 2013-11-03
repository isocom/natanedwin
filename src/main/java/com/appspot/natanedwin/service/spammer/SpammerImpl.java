package com.appspot.natanedwin.service.spammer;

import com.appspot.natanedwin.service.mailer.Email;
import com.appspot.natanedwin.service.mailer.Mailer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.stringtemplate.v4.ST;

/**
 *
 * @author prokob01
 */
@Service
public class SpammerImpl implements Spammer {

    @Autowired
    private Mailer mailer;

    @Override
    public void spam(SpamType spamType, Object... args) {
        String subject = spamType.getSubject();
        String textBody = spamType.getTextBody();
        String htmlBody = spamType.getHtmlBody();

        ST subj = new ST(subject, '$', '$');
        ST text = new ST(textBody, '$', '$');
        ST html = new ST(htmlBody, '$', '$');

        for (Object o : args) {
            subj.add(o.getClass().getSimpleName(), o);
            text.add(o.getClass().getSimpleName(), o);
            html.add(o.getClass().getSimpleName(), o);
        }

        subject = subj.render();
        textBody = text.render();
        htmlBody = html.render();

        Email message = new Email();
        message.addTo(spamType.getEmailAddress(args));
        message.setSubject(subject);
        message.setTextBody(textBody);
        message.setHtmlBody(htmlBody);

        mailer.send(message);
    }
}
