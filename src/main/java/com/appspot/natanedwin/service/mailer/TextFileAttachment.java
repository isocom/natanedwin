package com.appspot.natanedwin.service.mailer;

import java.io.UnsupportedEncodingException;

/**
 *
 * @author prokob01
 */
public class TextFileAttachment extends EmailAttachment {

    private final String content;

    public TextFileAttachment(String fileName, String content) {
        //super("text/plain; charset=UTF-8", fileName);
        super("application/octet-stream", fileName);
        this.content = content;
    }

    @Override
    public byte[] getContent() {
        try {
            return content.getBytes("UTF-8");
        } catch (UnsupportedEncodingException uee) {
            return uee.getMessage().getBytes();
        }
    }
}
