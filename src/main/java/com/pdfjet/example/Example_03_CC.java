package com.pdfjet.example;

import com.appspot.natanedwin.app.AppError;
import java.io.*;

import com.pdfjet.*;
import com.pdfjet.ext.CreditCard;
import org.apache.commons.io.FileUtils;

/**
 * Example_07.java
 *
 */
public class Example_03_CC {

    public static byte[] karta(String name, String numer, byte[] bmpImage) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PDF pdf = new PDF(baos, Compliance.PDF_A_1B);
            Font f1 = new Font(pdf, Example_03_CC.class.getResourceAsStream("/PDFjet/DejaVu/DejaVuLGCSerif.ttf.deflated"));
            f1.setSize(15f);
            Page page = new Page(pdf, CreditCard.LANDSCAPE);

            Image image3 = new Image(pdf, new ByteArrayInputStream(bmpImage), ImageType.BMP);
            image3.setLocation(0f, 0f);
            image3.scaleBy(CreditCard.WIDTH / image3.getWidth());
            image3.drawOn(page);

            TextLine text = new TextLine(f1);
            text.setText(name);
            text.setLocation(CreditCard.MIDDLE_X - text.getWidth() / 2, 110f);
            text.drawOn(page);

            text = new TextLine(f1);
            text.setText(numer);
            text.setLocation(CreditCard.MIDDLE_X - text.getWidth() / 2, 135f);
            text.drawOn(page);

            pdf.close();
            return baos.toByteArray();
        } catch (Throwable t) {
            throw new AppError("PDF problem: " + t.getMessage(), t.getMessage());
        }
    }

}
