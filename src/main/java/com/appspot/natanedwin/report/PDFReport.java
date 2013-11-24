package com.appspot.natanedwin.report;

import com.pdfjet.A4;
import com.pdfjet.CoreFont;
import com.pdfjet.Font;
import com.pdfjet.PDF;
import com.pdfjet.Page;
import java.io.ByteArrayOutputStream;

public class PDFReport {

    private final ByteArrayOutputStream fos = new ByteArrayOutputStream();
    private final PDF pdf;
    protected Page page;
    private final Font fontTimesRoman14;
    private final Font fontHelvetica14;
    private final Font fontTimesRoman10;
    private final Font fontHelvetica10;
    private final Font fontHelvetica6;
    protected double x_pos;
    protected double y_pos;
    private int pageNumber;

    public PDFReport() {
        try {
            pdf = new PDF(fos);
            fontTimesRoman14 = new Font(pdf, CoreFont.TIMES_ROMAN);
            fontTimesRoman14.setSize(14);
            fontHelvetica14 = new Font(pdf, CoreFont.HELVETICA);
            fontHelvetica14.setSize(14);
            fontTimesRoman10 = new Font(pdf, CoreFont.TIMES_ROMAN);
            fontTimesRoman10.setSize(10);
            fontHelvetica10 = new Font(pdf, CoreFont.HELVETICA);
            fontHelvetica10.setSize(10);
            fontHelvetica6 = new Font(pdf, CoreFont.HELVETICA);
            fontHelvetica6.setSize(6);
            pageNumber = 0;
        } catch (Exception t) {
            throw new RuntimeException(t);
        }
    }

    public ByteArrayStreamResource getReport() {
        try {
            pdf.flush();
            fos.close();
            byte[] toByteArray = fos.toByteArray();
            return new ByteArrayStreamResource(toByteArray);
        } catch (Exception t) {
            throw new RuntimeException(t);
        }
    }

    public Page newPage() {
        try {
            page = new Page(pdf, A4.PORTRAIT);
            x_pos = 20.0;
            y_pos = 20.0;
            pageNumber++;
            return page;
        } catch (Exception t) {
            throw new RuntimeException(t);
        }
    }

    public Font getFontHelvetica10() {
        return fontHelvetica10;
    }

    public Font getFontHelvetica14() {
        return fontHelvetica14;
    }

    public Font getFontHelvetica6() {
        return fontHelvetica6;
    }

    public Font getFontTimesRoman10() {
        return fontTimesRoman10;
    }

    public Font getFontTimesRoman14() {
        return fontTimesRoman14;
    }

    public int getPageNumber() {
        return pageNumber;
    }
}
