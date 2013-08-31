/**
 * Font.java * Copyright (c) 2007, 2008, 2009, 2010 Innovatics Inc.
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation and
 * / or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package com.pdfjet;

public class Font {

    protected String name = null;
    protected int objNumber = 0;
    // The object number of the embedded font file
    protected int fileObjNumber = -1;
    // Font attributes
    protected double size = 12.0;
    protected int unitsPerEm = 1000;
    protected double ascent = 0.0;
    protected double descent = 0.0;
    protected double body_height = 0.0;
    // Font metrics
    protected int[][] metrics = null;
    // Don't change the following default values!
    protected final boolean isStandard = true;
    protected boolean kernPairs = false;
    protected int firstChar = 32;
    protected int lastChar = 255;
    private final PDF pdf;
    private int codePage = CodePage.CP1250;
    // Font bounding box
    private double bBoxLLx = 0.0;
    private double bBoxLLy = 0.0;
    private double bBoxURx = 0.0;
    private double bBoxURy = 0.0;
    private int[] advanceWidths = null;
    private int[] glyphWidth = null;
    private int fontUnderlinePosition = 0;
    private int fontUnderlineThickness = 0;
    protected double underlinePosition = 0.0;
    protected double underlineThickness = 0.0;

    // Constructor for standard fonts
    public Font(PDF pdf, String fontName) throws Exception {
        this.pdf = pdf;
        this.name = fontName;

        pdf.newobj();
        pdf.append("<<\n");
        pdf.append("/Type /Font\n");
        pdf.append("/Subtype /Type1\n");
        pdf.append("/BaseFont /");
        pdf.append(fontName);
        pdf.append('\n');
        if (fontName.equals("Symbol") || fontName.equals("ZapfDingbats")) {
            // Use the built-in encoding
        } else {
            //pdf.append("/Encoding /WinAnsiEncoding\n");
            pdf.append("/Encoding " + pdf.bartEncodingObjectRef + " 0 R\n");
        }
        pdf.append(">>\n");
        pdf.endobj();
        objNumber = pdf.objNumber;

        CoreFont font = (CoreFont) Class.forName("com.pdfjet." + name.replace('-', '_')).newInstance();
        bBoxLLx = font.getBBoxLLx();
        bBoxLLy = font.getBBoxLLy();
        bBoxURx = font.getBBoxURx();
        bBoxURy = font.getBBoxURy();
        metrics = font.getMetrics();
        ascent = bBoxURy * size / unitsPerEm;
        descent = bBoxLLy * size / unitsPerEm;
        body_height = ascent - descent;

        fontUnderlineThickness = font.getUnderlineThickness();
        fontUnderlinePosition = font.getUnderlinePosition();

        underlineThickness = fontUnderlineThickness * size / unitsPerEm;
        underlinePosition = fontUnderlinePosition * size / -unitsPerEm + underlineThickness / 2.0;

        pdf.fonts.add(this);
    }

    public void setSize(double fontSize) {
        size = fontSize;
        ascent = bBoxURy * size / unitsPerEm;
        descent = bBoxLLy * size / unitsPerEm;
        body_height = ascent - descent;

        underlineThickness = fontUnderlineThickness * size / unitsPerEm;
        underlinePosition = fontUnderlinePosition * size / -unitsPerEm + underlineThickness / 2.0;
    }

    public double getSize() {
        return size;
    }

    public void setKernPairs(boolean kernPairs) {
        this.kernPairs = kernPairs;
    }

    public double stringWidth(String str) {
        int width = 0;

        for (int i = 0; i < str.length(); i++) {
            int c1 = str.charAt(i);

            if (c1 < firstChar || c1 > lastChar) {
                c1 = 32;
            }
            c1 -= 32;
            width += metrics[c1][1];

            if (kernPairs == false) {
                continue;
            }
            if (name.startsWith("C") || // Courier
                    name.startsWith("S") || // Symbol
                    name.startsWith("Z")) { // ZapfDingbats
                continue;
            }

            if (i == str.length() - 1) {
                break;
            }

            int c2 = str.charAt(i + 1);
            if (c2 < firstChar || c2 > lastChar) {
                c2 = 32;
            }
            for (int j = 2; j < metrics[c1].length; j += 2) {
                if (metrics[c1][j] == c2) {
                    width += metrics[c1][j + 1];
                    break;
                }
            }
        }

        return width * size / unitsPerEm;
    }

    private int nonStandardFontGlyphWidth(int c1) {
        int width = 0;
        {
            if (c1 < 127) {
                width = glyphWidth[c1];
            } else {
                if (codePage == 0) {
                    width = glyphWidth[CP1250.codes[c1 - 127]];
                } else if (codePage == 1) {
                    width = glyphWidth[CP1251.codes[c1 - 127]];
                } else if (codePage == 2) {
                    width = glyphWidth[CP1252.codes[c1 - 127]];
                } else if (codePage == 3) {
                    width = glyphWidth[CP1253.codes[c1 - 127]];
                } else if (codePage == 4) {
                    width = glyphWidth[CP1254.codes[c1 - 127]];
                } else if (codePage == 7) {
                    width = glyphWidth[CP1257.codes[c1 - 127]];
                }
            }
        }

        return width;
    }

    public double getAscent() {
        return ascent;
    }

    public double getDescent() {
        return -descent;
    }

    public double getHeight() {
        return ascent - descent;
    }

    public int getFitChars(String str, double width) {
        double w = width * unitsPerEm / size;
        return getStandardFontFitChars(str, w);
    }

    private int getStandardFontFitChars(String str, double width) {
        double w = width;

        int i;
        for (i = 0; i < str.length(); i++) {

            int c1 = str.charAt(i);

            if (c1 < firstChar || c1 > lastChar) {
                c1 = 32;
            }

            c1 -= 32;
            w -= metrics[c1][1];

            if (w < 0) {
                return i;
            }

            if (kernPairs == false) {
                continue;
            }

            if (name.startsWith("C") || // Courier
                    name.startsWith("S") || // Symbol
                    name.startsWith("Z")) { // ZapfDingbats
                continue;
            }

            if (i == str.length() - 1) {
                break;
            }

            int c2 = str.charAt(i + 1);
            if (c2 < firstChar || c2 > lastChar) {
                c2 = 32;
            }

            for (int j = 2; j < metrics[c1].length; j += 2) {

                if (metrics[c1][j] == c2) {
                    w -= metrics[c1][j + 1];
                    if (w < 0) {
                        return i;
                    }

                    break;
                }

            }

        }

        return i;
    }

    public double getBodyHeight() {
        return body_height;
    }
}