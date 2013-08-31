/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appspot.natanedwin.service.mailer;

/**
 *
 * @author prokob01
 */
public class ByteArrayAttachment extends EmailAttachment {

    private final byte[] content;

    public ByteArrayAttachment(String mimeType, String fileName, byte[] content) {
        super(mimeType, fileName);
        this.content = content;
    }

    @Override
    public byte[] getContent() {
        return content;
    }
}
