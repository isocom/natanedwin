package com.pdfjet.example;

import java.io.*;
import java.util.*;

import com.pdfjet.*;


/**
 *  Example_34.java
 *
 */
public class Example_34 {

    public Example_34() throws Exception {

        PDF pdf = new PDF(
                new BufferedOutputStream(new FileOutputStream("Example_34.pdf")));

        Page page = new Page(pdf, Letter.PORTRAIT);

        Font f1 = new Font(pdf, CoreFont.HELVETICA_BOLD);
        f1.setSize(7f);

        Font f2 = new Font(pdf, CoreFont.HELVETICA);
        f2.setSize(7f);

        Font f3 = new Font(pdf, CoreFont.HELVETICA_BOLD_OBLIQUE);
        f3.setSize(7f);

        FlexTable table = new FlexTable();
        List<List<AbstractCell>> tableData = getData(
        		"data/world-communications.txt", "|", Table.DATA_HAS_2_HEADER_ROWS, f1, f2);
        table.setData(tableData, Table.DATA_HAS_2_HEADER_ROWS);

        BufferedInputStream bis =
                new BufferedInputStream(new FileInputStream("images/fruit.jpg"));
        tableData.get(5).set(2, new ImageCell(new Image(pdf, bis, ImageType.JPG)));

        // table.setCellBordersWidth(1.2f);
        table.setLocation(70f, 30f);
        table.setTextColorInRow(6, Color.blue);
        table.setTextColorInRow(39, Color.red);
        table.setFontInRow(26, f3);
        table.removeLineBetweenRows(0, 1);  
        table.autoAdjustColumnWidths();
        table.setColumnWidth(0, 120.0f);
        table.rightAlignNumbers();
        int numOfPages = table.getNumberOfPages(page);
        while (true) {
            Point point = table.drawOn(page);
            // TO DO: Draw "Page 1 of N" here
            if (!table.hasMoreData()) {
                // Allow the table to be drawn again later:
                table.resetRenderedPagesCount();
                break;
            }
            page = new Page(pdf, Letter.PORTRAIT);
        }

        pdf.close();
    }
    
    
    public List<List<AbstractCell>> getData(
            String fileName,
            String delimiter,
            int numOfHeaderRows,
            Font f1,
            Font f2) throws Exception {

        List<List<AbstractCell>> tableData = new ArrayList<List<AbstractCell>>();

        int currentRow = 0;
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line = null;
        while ((line = reader.readLine()) != null) {
            List<AbstractCell> row = new ArrayList<AbstractCell>();
            String[] cols = null;
            if (delimiter.equals("|")) {
                cols = line.split("\\|", -1);
            }
            else if (delimiter.equals("\t")) {
                cols = line.split("\t", -1);
            }
            else {
                throw new Exception(
                		"Only pipes and tabs can be used as delimiters");
            }
            for (int i = 0; i < cols.length; i++) {
                String text = cols[i].trim();
                if (currentRow < numOfHeaderRows) {
                    row.add(new TextCell(f1, text));
                }
                else {
                    row.add(new TextCell(f2, text));
                }
            }
            tableData.add(row);
            currentRow++;
        }
        reader.close();

        appendMissingCells(tableData, f2);
        
        return tableData;
    }
    

    private void appendMissingCells(List<List<AbstractCell>> tableData, Font f2) {
        List<AbstractCell> firstRow = tableData.get(0);
        int numOfColumns = firstRow.size();
        for (int i = 0; i < tableData.size(); i++) {
            List<AbstractCell> dataRow = tableData.get(i);
            int dataRowColumns = dataRow.size();
            if (dataRowColumns < numOfColumns) {
                for (int j = 0; j < (numOfColumns - dataRowColumns); j++) {
                    dataRow.add(new TextCell(f2));
                }
                dataRow.get(dataRowColumns - 1).setColSpan((numOfColumns - dataRowColumns) + 1);
            }
        }
    }


    public static void main(String[] args) throws Exception {
        long time0 = System.currentTimeMillis();
        new Example_34();
        long time1 = System.currentTimeMillis();
        if (args.length > 0 && args[0].equals("time")) {
            System.out.println(time1 - time0);
        }
    }

}   // End of Example_34.java
