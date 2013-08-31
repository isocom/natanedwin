/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appspot.natanedwin.service.mailer;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author prokob01
 */
public class Email {

    private final List<EmailAddress> to = new ArrayList<EmailAddress>();
    private final List<EmailAddress> cc = new ArrayList<EmailAddress>();
    private final List<EmailAddress> bcc = new ArrayList<EmailAddress>();
    private String subject;
    private String textBody;
    private String htmlBody;
    private final List<EmailAttachment> attachments = new ArrayList<EmailAttachment>();

    public void addTo(EmailAddress mailAddress) {
        to.add(mailAddress);
    }

    public void addCc(EmailAddress mailAddress) {
        cc.add(mailAddress);
    }

    public void addBcc(EmailAddress mailAddress) {
        bcc.add(mailAddress);
    }

    public void addAttachment(EmailAttachment attachment) {
        attachments.add(attachment);
    }

    public List<EmailAddress> getTo() {
        return to;
    }

    public List<EmailAddress> getCc() {
        return cc;
    }

    public List<EmailAddress> getBcc() {
        return bcc;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTextBody() {
        return textBody;
    }

    public void setTextBody(String textBody) {
        this.textBody = textBody;
    }

    public String getHtmlBody() {
        return htmlBody;
    }

    public void setHtmlBody(String htmlBody) {
        this.htmlBody = htmlBody;
    }

    public List<EmailAttachment> getAttachments() {
        return attachments;
    }
}
