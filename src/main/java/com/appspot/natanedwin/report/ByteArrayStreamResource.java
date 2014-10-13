package com.appspot.natanedwin.report;

import com.vaadin.server.StreamResource;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class ByteArrayStreamResource implements StreamResource.StreamSource {

    final private byte[] bytes;

    public ByteArrayStreamResource(final byte[] bytes) {
        this.bytes = bytes;
    }

    @Override
    public InputStream getStream() {
        return new ByteArrayInputStream(bytes);
    }
}
