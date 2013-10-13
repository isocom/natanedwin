package com.pdfjet.example;

import java.io.*;

import com.pdfjet.*;

/**
 * Example_07.java
 *
 */
public class Example_07_PL {

    public Example_07_PL() throws Exception {

        PDF pdf = new PDF(
                new BufferedOutputStream(
                new FileOutputStream("/opt/temp/Example_07_PL.pdf")),
                Compliance.PDF_A_1B);

        // Commercial and Open Source versions:
        String fontName = "DejaVuLGCSerif";
        Font f1 = new Font(pdf,
                getClass().getResourceAsStream("/pdf-jet/DejaVu/ttf/" + fontName + ".ttf.deflated"));

        Font f2 = new Font(pdf,
                getClass().getResourceAsStream("/pdf-jet/DejaVu/ttf/" + fontName + ".ttf.deflated"));
        f2.setItalic(true);

        Page page = new Page(pdf, Letter.PORTRAIT);

        f1.setSize(15f);
        f2.setSize(15f);

        float x_pos = 70f;
        float y_pos = 70f;
        TextLine text = new TextLine(f1);
        text.setLocation(x_pos, y_pos);
        text.setText("Alła ma kota. Zażółć gęślą jaźń.");
        text.drawOn(page);
        pdf.close();
    }

    public static void main(String[] args) throws Exception {
        // long time0 = System.currentTimeMillis();
        new Example_07_PL();
        // long time1 = System.currentTimeMillis();
        // System.out.println(time1 - time0);
    }
}   // End of Example_07.java
