package com.pdfjet.example;

import java.io.*;
import java.util.*;

import com.pdfjet.*;


/**
 *  Example_21.java
 *
 */
class Example_21 {

    public Example_21(String fileName) throws Exception {

        try {

            PDF pdf = new PDF();

            BufferedInputStream bis =
                    new BufferedInputStream(
                            new FileInputStream(fileName));

            Map<Integer, PDFobj> objects = pdf.read(bis);
            bis.close();

            for (PDFobj obj : objects.values()) {
/*
Uncomment to print out all the objects in a textual form:
                for (int i = 0; i < obj.dict.size(); i++) {
                    System.out.println(obj.dict.get(i));
                }
                System.out.println();
*/
                String type = obj.getValue(PDFobj.TYPE);
                if (type.equals("/Font")
                        && obj.getValue(PDFobj.SUBTYPE).equals("/Type0") == false
                        && obj.getValue("/FontDescriptor").equals("")) {

                    System.out.println("Non-EmbeddedFont -> "
                            + obj.getValue("/BaseFont").substring(1));
                }
                else if (type.equals("/FontDescriptor")) {
/*
                    for (int i = 0; i < obj.dict.size(); i++) {
                        System.out.println(obj.dict.get(i));
                    }
                    System.out.println();
*/
                    String fontFile = obj.getValue("/FontFile");
                    if (fontFile.equals("")) {
                        fontFile = obj.getValue("/FontFile2");
                    }
                    if (fontFile.equals("")) {
                        fontFile = obj.getValue("/FontFile3");
                    }

                    if (fontFile.equals("")) {
                        System.out.println("Non-Embedded Font -> "
                                + obj.getValue("/FontName").substring(1));
                    }
                }
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static void main(String[] args) {
        if (args.length > 0) {
            try {
                new Example_21(args[0]);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}   // End of Example_21.java
