package com.appspot.natanedwin.service.mailer;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 *
 * @author prokob01
 */
public class ByteArrayAttachment extends EmailAttachment {

    private final byte[] content;

    @SuppressFBWarnings("EI_EXPOSE_REP2")
    public ByteArrayAttachment(String mimeType, String fileName, byte[] content) {
        super(mimeType, fileName);
        this.content = content;
    }

    @SuppressFBWarnings("EI_EXPOSE_REP")
    @Override
    public byte[] getContent() {
        return content;
    }
}
