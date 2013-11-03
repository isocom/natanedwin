package com.appspot.natanedwin.service.gcs;

public interface Gcs {

    public void delete(final String bucketName, final String objectName);

    public byte[] read(final String bucketName, final String objectName);

    public void write(final String bucketName, final String objectName, GcsMimeType mimeType, byte[] content);
}
