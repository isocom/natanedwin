package com.appspot.natanedwin.service.gcs;

import com.google.appengine.tools.cloudstorage.GcsFileOptions;
import com.google.appengine.tools.cloudstorage.GcsFileOptions.Builder;

public enum GcsMimeType {

    Default("binary/octet-stream") {

                @Override
                public GcsFileOptions getGcsFileOptions() {
                    return GcsFileOptions.getDefaultInstance();
                }

            },
    /**
     * Bitmaps - used mainly for card printing
     */
    BMP("image/bmp") {
                private final GcsFileOptions gcsFileOptions = new Builder().mimeType(mimeType).build();

                @Override
                public GcsFileOptions getGcsFileOptions() {
                    return gcsFileOptions;
                }
            };

    protected final String mimeType;

    private GcsMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public abstract GcsFileOptions getGcsFileOptions();

    public static GcsMimeType dicoverMimeType(String mimeType) {
        if (BMP.getMimeType().equalsIgnoreCase(mimeType)) {
            return BMP;
        }
        return Default;
    }

    public String getMimeType() {
        return mimeType;
    }

}
