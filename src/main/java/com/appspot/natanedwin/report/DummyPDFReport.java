package com.appspot.natanedwin.report;

import com.pdfjet.Page;
import com.pdfjet.TextLine;

public class DummyPDFReport {

    public static ByteArrayStreamResource asPDF() {
        try {
            PDFReport pdfReport = new PDFReport();
            Page newPage = pdfReport.newPage();

            TextLine text = new TextLine(pdfReport.getFontHelvetica14());
            text.setText("Wydruk PDF w opracowaniu");
            text.setPosition(100, 100);
            text.drawOn(newPage);

            return pdfReport.getReport();
        } catch (Exception t) {
            throw new RuntimeException(t);
        }
    }

}
