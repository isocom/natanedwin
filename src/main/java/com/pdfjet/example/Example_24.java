package com.pdfjet.example;

import java.io.*;

import com.pdfjet.*;


/**
 *  Example_24.java
 *
 */
public class Example_24 {

    public Example_24() throws Exception {

        PDF pdf = new PDF(
                new BufferedOutputStream(
                        new FileOutputStream("Example_24.pdf")));

        Font f1 = new Font(pdf, CoreFont.HELVETICA);

        for (int i = 0; i < 500; i++) {
            Page page = new Page(pdf, Letter.PORTRAIT);

            TextLine text = new TextLine(
                    f1, "We are currently generating page = " + i);
            text.setTextDirection(0);
            text.setLocation(100f, 100f);
            text.drawOn(page);

            JPGImage jpg1 = new JPGImage(null,
                    new BufferedInputStream(new FileInputStream("images/fruit.jpg")));
            JPGImage jpg2 = new JPGImage(jpg1,
                    new BufferedInputStream(new FileInputStream("images/fruit.jpg")));
            Image image = new Image(pdf, jpg2);
            image.setLocation(100f, 120f);
            image.drawOn(page);

            String path = "images/mt-map.rgb.330x355x4.raw";
            InputStream stream = new FileInputStream(path);
            File file = new File(path);
            image = new Image(pdf, new PDFImage(path, stream, file.length()));
            image.setLocation(100f, 400f);
            image.drawOn(page);
        }

        pdf.close();
    }


    public static void main(String[] args) {
        try {
            if (args.length > 0 && args[0].equals("time")) {
                long time0 = System.currentTimeMillis();
                new Example_24();
                long time1 = System.currentTimeMillis();
                System.out.println(time1 - time0);
            }
            else {
                new Example_24();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}   // End of Example_24.java
