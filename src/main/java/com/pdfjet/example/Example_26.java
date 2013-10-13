package com.pdfjet.example;

import java.io.*;

import com.pdfjet.*;


/**
 *  Example_26.java
 *
 */
public class Example_26 {

    public Example_26() throws Exception {

        PDF pdf = new PDF(
                new BufferedOutputStream(
                        new FileOutputStream("Example_26.pdf")));

        Font f1 = new Font(pdf, CoreFont.HELVETICA_BOLD);
        f1.setSize(12f);

        Font f2 = new Font(pdf, CoreFont.HELVETICA_BOLD);
        f2.setSize(20f);

        Page page = new Page(pdf, Letter.PORTRAIT);

        float x = 50f;
        float y = 50f;

        CheckBox chkBox = new CheckBox();
        chkBox.setLocation(x, y);
        chkBox.setBoxColor(Color.oldgloryblue);
        chkBox.setCheckColor(Color.blue);
        chkBox.drawOn(page);

        x += (chkBox.getWidth() + 4f);

        TextLine label = new TextLine(f1, "I am tall");
        label.setLocation(x, y + label.getFont().getAscent());
        label.drawOn(page);

        x = 150f;

        chkBox = new CheckBox(false);
        chkBox.setLocation(x, y);
        chkBox.setBoxColor(Color.oldgloryblue);
        chkBox.setCheckColor(Color.blue);
        chkBox.drawOn(page);

        x += (chkBox.getWidth() + 4f);

        label = new TextLine(f1, "Fluffy is tall");
        label.setLocation(x, y + label.getFont().getAscent());
        label.drawOn(page);

        x = 300f;

        chkBox = new CheckBox();
        chkBox.setLocation(x, y);
        chkBox.setBoxColor(Color.oldgloryblue);
        chkBox.setCheckColor(Color.black);
        chkBox.setSize(20f);
        chkBox.drawOn(page);

        x += (chkBox.getWidth() + 4f);

        label = new TextLine(f2, "Bowser is big!");
        label.setLocation(x, y + label.getFont().getAscent());
        label.setColor(Color.oldgloryblue);
        label.drawOn(page);

        y += 25f;
        x = 50f;

        chkBox = new CheckBox(false);
        chkBox.setLocation(x, y);
        chkBox.drawOn(page);

        x += (chkBox.getWidth() + 4f);

        label = new TextLine(f1, "I am short");
        label.setLocation(x, y + label.getFont().getAscent());
        label.drawOn(page);

        x = 150f;

        chkBox = new CheckBox(true, Color.red);
        chkBox.setLocation(x, y);
        chkBox.setBoxColor(Color.blue);
        chkBox.setMarkType(2);
        chkBox.drawOn(page);

        x += (chkBox.getWidth() + 4f);

        label = new TextLine(f1, "Fluffy is short");
        label.setLocation(x, y + label.getFont().getAscent());
        label.drawOn(page);

        CheckBox checkBox = new CheckBox(f1, "Hello, World.");
        checkBox.setChecked(true);
        checkBox.setLocation(50f, 100f);
        checkBox.drawOn(page);

        pdf.close();
    }


    public static void main(String[] args) {
        try {
            new Example_26();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}   // End of Example_26.java
