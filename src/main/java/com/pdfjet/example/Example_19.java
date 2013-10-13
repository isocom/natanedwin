package com.pdfjet.example;

import java.io.*;
import java.util.*;

import com.pdfjet.*;


/**
 *  Example_19.java
 *
 */
class Example_19 {

    public Example_19() throws Exception {

        try {
            // An existing PDF file to read:
            BufferedInputStream bis =
                    new BufferedInputStream(new FileInputStream("Example_03.pdf"));

            // A new PDF file to create:
            PDF pdf = new PDF(
                    new BufferedOutputStream(
                            new FileOutputStream("Example_19.pdf")));

            Map<Integer, PDFobj> objects = pdf.read(bis);
            bis.close();
/*
            for (PDFobj obj : objects.values()) {
                for (int i = 0; i < obj.dict.size(); i++) {
                    System.out.println(obj.dict.get(i));
                }
                System.out.println();
            }
*/
            for (PDFobj obj : objects.values()) {
                if (obj.getValue("/Type").equals("/Font")) {
                    new Font(pdf, obj);
                }
                else if (obj.getValue("/Type").equals("/XObject")) {
                    if (obj.getValue("/Subtype").equals("/Image")) {
                        new Image(pdf, obj);
                    }
                }
                else if (obj.getValue("/Type").equals("/Page")) {
                    int objNumber = Integer.valueOf(obj.getValue("/Contents"));
                    Page page = new Page(pdf, obj.getPageSize());
                    page.append(objects.get(objNumber).getData());
                    page.setPenWidth(1.5f);
                    page.drawRect(250f, 210f, 50f, 50f);
                }
            }

            pdf.close();
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }

    }


    public static void main(String[] args) throws Exception {
        new Example_19();
    }

}   // End of Example_19.java
