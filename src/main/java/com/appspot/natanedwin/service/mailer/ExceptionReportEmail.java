package com.appspot.natanedwin.service.mailer;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 *
 * @author prokob01
 */
public class ExceptionReportEmail extends Email {

    public ExceptionReportEmail(Throwable exception) {
        addTo(new EmailAddress("BPP", "prokop.bart@gmail.com"));
        addTo(new EmailAddress("E-Dziecko", "edziecko@isocom.eu"));
        setSubject(exception.getClass().toString() + ": " + exception.getMessage());
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            try (PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(byteArrayOutputStream, "UTF-8"))) {
                printWriter.println("Raised exception:");
                exception.printStackTrace(printWriter);
                printWriter.flush();
            }
            setTextBody(new String(byteArrayOutputStream.toByteArray(), "UTF-8"));
        } catch (UnsupportedEncodingException uee) {
            throw new RuntimeException(uee);
        }
    }
}
