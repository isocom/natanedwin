package com.appspot.natanedwin.report;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import jxl.Workbook;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class XLSReport {

    private ByteArrayOutputStream baos = new ByteArrayOutputStream();
    private WritableWorkbook writableWorkbook;

    public XLSReport() {
        try {
            writableWorkbook = Workbook.createWorkbook(baos);
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    public ByteArrayStreamResource getReport() {
        try {
            writableWorkbook.write();
            writableWorkbook.close();
            byte[] byteArray = baos.toByteArray();
            return new ByteArrayStreamResource(byteArray);
        } catch (IOException | WriteException t) {
            throw new RuntimeException(t);
        }
    }

    public WritableWorkbook getWritableWorkbook() {
        return writableWorkbook;
    }
}
