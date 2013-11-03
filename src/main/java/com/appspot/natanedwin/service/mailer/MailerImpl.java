/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appspot.natanedwin.service.mailer;

import com.google.appengine.api.mail.MailService;
import com.google.appengine.api.mail.MailServiceFactory;
import java.io.IOException;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 *
 * @author prokob01
 */
@Service
public class MailerImpl implements Mailer {

    private final static Logger LOGGER = LoggerFactory.getLogger(MailerImpl.class);
    @Value("${service.mailer.fromAddr}")
    private String fromAddr;
    @Value("${service.mailer.fromName}")
    private String fromName;
    private Session session = Session.getDefaultInstance(new Properties(), null);
    private MailService mailService = MailServiceFactory.getMailService();

    @Override
    public void send(Email email) {
        if (email == null) {
            throw new IllegalArgumentException("email == null");
        }

        try {
            if (email.getTextBody() != null && email.getHtmlBody() == null && email.getAttachments().isEmpty()) {
                sendMimeMessage(email);
            } else {
                sendMimeMultipart(email);
            }
        } catch (Exception e) {
            throw new RuntimeException("Bład w mailerze", e);
        }
    }

    private void sendMimeMessage(Email email) throws Exception {
        MimeMessage msg = new MimeMessage(session);
        commonMessageProcessing(msg, email);
        msg.setText(email.getTextBody());
        Transport.send(msg);
    }

    private void sendMimeMultipart(Email email) throws Exception {
        final Multipart multipart = new MimeMultipart();

        if (email.getTextBody() != null) {
            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setText(email.getTextBody());
            multipart.addBodyPart(textPart);
        }

        if (email.getHtmlBody() != null) {
            MimeBodyPart htmlPart = new MimeBodyPart();
            htmlPart.setContent(email.getHtmlBody(), "text/html");
            multipart.addBodyPart(htmlPart);
        }

        for (EmailAttachment attachment : email.getAttachments()) {
            MimeBodyPart attachmentPart = new MimeBodyPart();
            attachmentPart.setDisposition(MimeBodyPart.ATTACHMENT);
            attachmentPart.setFileName(attachment.getFileName());
            ByteArrayDataSource dataSource = new ByteArrayDataSource(attachment.getContent(), attachment.getMimeType());
            attachmentPart.setDataHandler(new DataHandler(dataSource));
            multipart.addBodyPart(attachmentPart);
        }

        final MimeMessage message = new MimeMessage(session);
        commonMessageProcessing(message, email);
        message.setContent(multipart);
        Transport.send(message);
    }

    private void commonMessageProcessing(MimeMessage msg, Email email) throws Exception {
        msg.setFrom(new InternetAddress(fromAddr, fromName));
        for (EmailAddress mailAddress : email.getTo()) {
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(mailAddress.getEmailAddress(), mailAddress.getFullName(), "UTF-8"));
        }
        for (EmailAddress mailAddress : email.getCc()) {
            msg.addRecipient(Message.RecipientType.CC, new InternetAddress(mailAddress.getEmailAddress(), mailAddress.getFullName(), "UTF-8"));
        }
        for (EmailAddress mailAddress : email.getBcc()) {
            msg.addRecipient(Message.RecipientType.BCC, new InternetAddress(mailAddress.getEmailAddress(), mailAddress.getFullName(), "UTF-8"));
        }
        msg.addRecipient(Message.RecipientType.BCC, new InternetAddress("prokop.bart@gmail.com", "Bartłomiej Prokop", "UTF-8"));
        msg.setSubject(email.getSubject(), "UTF-8");
    }

    @Override
    public void sendToAdmins(String subject, String body) {
        try {
            MailService.Message message = new MailService.Message(fromAddr, null, subject, body);
            mailService.sendToAdmins(message);
        } catch (IOException ioe) {
            LOGGER.error("Unable to send admin message", ioe);
        }
    }
}
