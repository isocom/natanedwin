package com.pdfjet.ext;

/**
 * Used to specify PDF page with size <strong>CreditCard</strong>. For more
 * information about the page size classes - A3, A4, A5, B5, Executive, Letter,
 * Legal and Tabloid - see the Page class.
 *
 */
public class CreditCard {

    /**
     * 3 ⅜ × 2 ⅛
     */
    public static final float[] LANDSCAPE = new float[]{(3f + 3f / 8f) * 72.0f, (2f + 1f / 8f) * 72.0f};
    public static final float MIDDLE_X = LANDSCAPE[0] / 2f;
    public static final float MIDDLE_Y = LANDSCAPE[1] / 2f;
    public static final float WIDTH = LANDSCAPE[0];
    public static final float HEIGHT = LANDSCAPE[1];
}
