package com.pdfjet.example;

import java.io.*;

import com.pdfjet.*;

/**
 * Example_01.java We will draw the American flag using Box, Line and Point
 * objects.
 */
public class Example_01 {

    public Example_01() throws Exception {

        PDF pdf = new PDF(
                new BufferedOutputStream(
                new FileOutputStream("/opt/temp/Example_01.pdf")));

        Page page = new Page(pdf, Letter.PORTRAIT);

        Box flag = new Box();
        flag.setLocation(100f, 100f);
        flag.setSize(190f, 100f);
        flag.setColor(Color.white);
        flag.drawOn(page);

        float sw = 7.69f;   // stripe width
        Line stripe = new Line(0.0f, sw / 2, 190.0f, sw / 2);
        stripe.setWidth(sw);
        stripe.setColor(Color.oldgloryred);
        for (int row = 0; row < 7; row++) {
            stripe.placeIn(flag, 0.0f, row * 2 * sw);
            stripe.drawOn(page);
        }

        Box union = new Box();
        union.setSize(76.0f, 53.85f);
        union.setColor(Color.oldgloryblue);
        union.setFillShape(true);
        union.placeIn(flag, 0f, 0f);
        union.drawOn(page);

        float h_si = 12.6f; // horizontal star interval
        float v_si = 10.8f; // vertical star interval
        Point star = new Point(h_si / 2, v_si / 2);
        star.setShape(Point.STAR);
        star.setRadius(3.0f);
        star.setColor(Color.white);
        star.setFillShape(true);

        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 5; col++) {
                star.placeIn(union, row * h_si, col * v_si);
                star.drawOn(page);
            }
        }
        star.setLocation(h_si, v_si);
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 4; col++) {
                star.placeIn(union, row * h_si, col * v_si);
                star.drawOn(page);
            }
        }

        pdf.close();
    }

    public static void main(String[] args) {
        try {
            new Example_01();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}   // End of Example_01.java
