/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appspot.natanedwin.service.mailer;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;

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
        PrintWriter printWriter = new PrintWriter(byteArrayOutputStream);
        printWriter.println("Raised exception:");
        exception.printStackTrace(printWriter);
        printWriter.flush();
        printWriter.close();
        setTextBody(new String(byteArrayOutputStream.toByteArray()));
    }
}
