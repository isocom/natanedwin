package com.appspot.natanedwin.service.gcs;

import com.google.appengine.tools.cloudstorage.GcsFilename;
import com.google.appengine.tools.cloudstorage.GcsOutputChannel;
import com.google.appengine.tools.cloudstorage.GcsService;
import com.google.appengine.tools.cloudstorage.GcsServiceFactory;
import com.google.appengine.tools.cloudstorage.RetryParams;
import java.io.IOException;
import java.nio.ByteBuffer;
import org.springframework.stereotype.Service;

@Service
public class GcsImpl implements Gcs {

    private final GcsService gcsService = GcsServiceFactory.createGcsService(RetryParams.getDefaultInstance());

    @Override
    public void write(final String objectName, GcsMimeType mimeType, byte[] content) {
        try {
            GcsFilename fileName = new GcsFilename("natanedwin", objectName);
            try (GcsOutputChannel outputChannel = gcsService.createOrReplace(fileName, mimeType.getGcsFileOptions())) {
                outputChannel.write(ByteBuffer.wrap(content));
            }
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }
}
