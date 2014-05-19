package com.appspot.natanedwin.app;

import com.appspot.natanedwin.service.appsession.AppSession;
import com.appspot.natanedwin.service.mailer.Mailer;
import com.appspot.natanedwin.service.spring.SpringContext;
import com.vaadin.server.DefaultErrorHandler;
import com.vaadin.server.ErrorEvent;
import com.vaadin.ui.Notification;
import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

/**
 *
 * @author prokob01
 */
@Configurable
public class AppErrorListener extends DefaultErrorHandler {

    static final long serialVersionUID = -8047759949255433960L;
    @Autowired
    private transient Mailer mailer;

    @Override
    public void error(ErrorEvent event) {
        //super.error(event); //To change body of generated methods, choose Tools | Templates.
        if (event.getThrowable() instanceof AppError) {
            displayAppError(event);
        } else {
            displayNotification(event);
            sendExceptionMail(event);
        }
    }

    private void displayAppError(ErrorEvent event) {
        AppError appError = (AppError) event.getThrowable();
        Notification.show(appError.getMessage(), appError.getDescription(), Notification.Type.HUMANIZED_MESSAGE);
    }

    private void displayNotification(ErrorEvent event) {
        StringBuilder sb = new StringBuilder();
        Throwable throwable = event.getThrowable();
        int i = 0;
        while (throwable != null) {
            String message;
            if (throwable.getMessage() == null) {
                message = "C: " + throwable.getClass().getName();
            } else {
                message = "M: " + throwable.getMessage();
            }
            sb.append(++i).append(". ").append(message).append("\n");
            throwable = throwable.getCause();
        }
        Notification.show(sb.toString(), Notification.Type.ERROR_MESSAGE);
    }

    private void sendExceptionMail(ErrorEvent event) {
        AppSession appSession = SpringContext.INSTANCE.getBean(AppSession.class);
        StringBuilder sb = new StringBuilder();
        String subject = "no Throwable assigned";

        sb.append("Użytkownik: ").append(appSession.getUserAccount()).append("\n");
        Throwable throwable = event.getThrowable();
        while (throwable != null) {
            sb.append("Klasa: ").append(throwable.getClass()).append(".");
            sb.append("Błąd: ").append(throwable.getMessage()).append("\n");
            throwable = throwable.getCause();
            if (throwable != null) {
                subject = throwable.getClass().getSimpleName();
            }
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            try (PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(byteArrayOutputStream, "UTF-8"))) {
                event.getThrowable().printStackTrace(printWriter);
                printWriter.flush();
            }
            sb.append(byteArrayOutputStream.toString("UTF-8"));
        } catch (UnsupportedEncodingException uee) {
        }
        mailer.sendToAdmins("GAE Problem: " + subject, sb.toString());
    }
}
