package com.appspot.natanedwin.servlet.tools;

import com.appspot.natanedwin.dao.GcsFileDao;
import com.appspot.natanedwin.entity.GcsFile;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

@Configurable
public class GcsFileUploadServlet extends HttpServlet {

    static final long serialVersionUID = 270327454305674861L;
    @Autowired
    private transient GcsFileDao gcsFileDao;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        PrintWriter writer = res.getWriter();
        try {
            ServletFileUpload upload = new ServletFileUpload();
            res.setContentType("text/plain");

            String fileName = "";
            String md5 = "";
            String sha1 = "";
            byte[] content = null;
            String renameTo = null;
            String description = "";

            FileItemIterator iterator = upload.getItemIterator(req);
            while (iterator.hasNext()) {
                FileItemStream item = iterator.next();
                InputStream stream = item.openStream();

                if (item.isFormField()) {
                    writer.println("Got a form field: " + item.getFieldName());
                    if ("description".equals(item.getFieldName())) {
                        description = Streams.asString(stream, "UTF-8");
                    }
                    if ("renameTo".equals(item.getFieldName())) {
                        renameTo = Streams.asString(stream, "UTF-8");
                    }
                    writer.println("description: " + description);
                    writer.println("renameTo   : " + renameTo);
                } else {
                    fileName = item.getName();
                    writer.println("Got an uploaded file: " + item.getFieldName() + ", name = " + fileName + ", content/type = " + item.getContentType());

                    // You now have the filename (item.getName() and the
                    // contents (which you can read from stream). Here we just
                    // print them back out to the servlet output stream, but you
                    // will probably want to do something more interesting (for
                    // example, wrap them in a Blob and commit them to the
                    // datastore).
                    int len;
                    byte[] buffer = new byte[8192];
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    while ((len = stream.read(buffer, 0, buffer.length)) != -1) {
                        baos.write(buffer, 0, len);
                    }
                    content = baos.toByteArray();
                    md5 = Hex.encodeHexString(DigestUtils.md5(content));
                    sha1 = Hex.encodeHexString(DigestUtils.sha1(content));
                }
            }

            if (renameTo != null) {
                fileName = "app/rfidcard/" + renameTo;
            } else {
                fileName = "app/rfidcard/" + fileName;
            }

            GcsFile gcsFile = new GcsFile();
            gcsFile.setMd5sum(md5);
            gcsFile.setSha1sum(sha1);
            gcsFile.setObjectName(fileName);
            gcsFile.setDescription(description);

            writer.println("-----------------------------------------");
            writer.println("md5:         " + md5);
            writer.println("sha1:        " + sha1);
            writer.println("saved as:    " + fileName);
            writer.println("description: " + description);
            writer.println("-----------------------------------------");

            gcsFileDao.save(gcsFile, content);
//
//            Gcs gcs = SpringContext.INSTANCE.getBean(Gcs.class);
//            gcs.write(fileName, GcsMimeType.BMP, content);
        } catch (Throwable t) {
            t.printStackTrace(writer);
        }
    }
}
