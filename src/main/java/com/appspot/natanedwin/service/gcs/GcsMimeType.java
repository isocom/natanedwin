package com.appspot.natanedwin.service.gcs;

import com.google.appengine.tools.cloudstorage.GcsFileOptions;
import com.google.appengine.tools.cloudstorage.GcsFileOptions.Builder;

public enum GcsMimeType {

    Default {

                @Override
                public GcsFileOptions getGcsFileOptions() {
                    return GcsFileOptions.getDefaultInstance();
                }

            },
    /**
     * Bitmaps - used mainly for card printing
     */
    BMP {
                private final GcsFileOptions gcsFileOptions = new Builder().mimeType("image/bmp").build();

                @Override
                public GcsFileOptions getGcsFileOptions() {
                    return gcsFileOptions;
                }
            };

    public abstract GcsFileOptions getGcsFileOptions();
}
