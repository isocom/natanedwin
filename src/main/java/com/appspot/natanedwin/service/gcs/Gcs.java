package com.appspot.natanedwin.service.gcs;

public interface Gcs {

    public void write(final String objectName, GcsMimeType mimeType, byte[] content);
}
