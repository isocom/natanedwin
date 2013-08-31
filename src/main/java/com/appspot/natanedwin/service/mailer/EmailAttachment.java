/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appspot.natanedwin.service.mailer;

import java.io.Serializable;

/**
 *
 * @author prokob01
 */
public abstract class EmailAttachment implements Serializable {

    private final String mimeType;
    private final String fileName;

    public EmailAttachment(String mimeType, String fileName) {
        this.mimeType = mimeType;
        this.fileName = fileName;
    }

    public String getMimeType() {
        return mimeType;
    }

    public String getFileName() {
        return fileName;
    }

    public abstract byte[] getContent();
}
