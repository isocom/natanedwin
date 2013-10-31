package com.appspot.natanedwin.service.gcs;

public interface Gcs {

    public byte[] read(final String objectName);

    public void write(final String objectName, GcsMimeType mimeType, byte[] content);
}
