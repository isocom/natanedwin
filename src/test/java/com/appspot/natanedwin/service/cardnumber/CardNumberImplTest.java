/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appspot.natanedwin.service.cardnumber;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author prokob01
 */
public class CardNumberImplTest {

    public CardNumberImplTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of compress method, of class CardNumberImpl.
     */
    @Test
    public void testCompress() {
        System.out.println("compress");
        CardNumberImpl i = new CardNumberImpl();
        assertEquals("1234567890123456", i.compress("1234 5678 9012 3456"));
    }

    /**
     * Test of pretty method, of class CardNumberImpl.
     */
    @Test
    public void testPretty() {
        System.out.println("pretty");
        CardNumberImpl i = new CardNumberImpl();
        assertEquals("1234 5678 9012 3456", i.pretty("1234567890123456"));
    }

    /**
     * Test of pretty method, of class CardNumberImpl.
     */
    @Test
    public void testBuildIIS() {
        System.out.println("pretty");
        CardNumberImpl i = new CardNumberImpl();
        assertEquals(6, CardNumberImpl.buildIIN().length());
    }

    @Test
    public void testGenerateProposal() {
        System.out.println("generateProkposal");
        CardNumberImpl i = new CardNumberImpl();
        for (int j = 0; j < 1000; j++) {
            String p = i.generateProposal();
            System.out.println(p);
            assertEquals(16+3, p.length());
        }
    }
}
