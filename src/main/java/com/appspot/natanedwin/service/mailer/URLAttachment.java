/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appspot.natanedwin.service.mailer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 *
 * @author prokob01
 */
public class URLAttachment extends EmailAttachment {

    private final URL url;

    public URLAttachment(String mimeType, String fileName, URL url) {
        super(mimeType, fileName);
        this.url = url;
    }

    @Override
    public byte[] getContent() {
        InputStream is = null;
        try {
            URLConnection openConnection = url.openConnection();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[512];
            is = url.openStream();
            int len;
            while ((len = is.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            return baos.toByteArray();
        } catch (IOException e) {
            return null;
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException ioe) {
                }
            }
        }
    }
}
