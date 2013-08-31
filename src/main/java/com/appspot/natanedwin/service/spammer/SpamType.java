/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appspot.natanedwin.service.spammer;

import com.appspot.natanedwin.entity.UserAccount;
import com.appspot.natanedwin.service.mailer.EmailAddress;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 *
 * @author prokob01
 */
public enum SpamType {

    UserGoogleAccount("Nowe konto w systemie ISOCOM"),
    UserAccountCreated("Utworzono nowe konto dostępu do KD/RPC ISOCOM");
    private final String subject;

    private SpamType(String subject) {
        this.subject = subject;
    }

    private String getTemplate(String templatePath) {
        try {
            Resource resource = new ClassPathResource(templatePath);
            return IOUtils.toString(resource.getInputStream(), "UTF-8");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public EmailAddress getEmailAddress(Object... objects) {
        for (Object o : objects) {
            if (o instanceof UserAccount) {
                UserAccount userAccount = (UserAccount) o;
                return new EmailAddress(userAccount.getUserId(), userAccount.getEmail().getEmail());
            }
        }
        throw new IllegalStateException();
    }

    public String getSubject() {
        return subject;
    }

    public String getTextBody() {
        return getTemplate("/templates/" + name() + ".txt");
    }

    public String getHtmlBody() {
        return getTemplate("/templates/" + name() + ".html");
    }
}
