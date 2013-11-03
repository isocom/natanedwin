package com.appspot.natanedwin.service.gcs;

import com.google.appengine.tools.cloudstorage.GcsFilename;
import com.google.appengine.tools.cloudstorage.GcsInputChannel;
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
    public void delete(final String bucketName, final String objectName) {
        try {
            GcsFilename fileName = new GcsFilename(bucketName, objectName);
            gcsService.delete(fileName);
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

    @Override
    public byte[] read(final String bucketName, String objectName) {
        try {
            GcsFilename fileName = new GcsFilename(bucketName, objectName);
            int fileSize = (int) gcsService.getMetadata(fileName).getLength();
            ByteBuffer result = ByteBuffer.allocate(fileSize);
            try (GcsInputChannel readChannel = gcsService.openReadChannel(fileName, 0)) {
                readChannel.read(result);
            }
            return result.array();
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

    @Override
    public void write(final String bucketName, final String objectName, GcsMimeType mimeType, byte[] content) {
        try {
            GcsFilename fileName = new GcsFilename(bucketName, objectName);
            try (GcsOutputChannel outputChannel = gcsService.createOrReplace(fileName, mimeType.getGcsFileOptions())) {
                outputChannel.write(ByteBuffer.wrap(content));
            }
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

}
