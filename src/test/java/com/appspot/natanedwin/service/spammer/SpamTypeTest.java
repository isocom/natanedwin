/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appspot.natanedwin.service.spammer;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author prokob01
 */
public class SpamTypeTest {

    public SpamTypeTest() {
    }

    @Test
    public void testSomeMethod() {
        for (SpamType st : SpamType.values()) {
            System.out.println(st);
            System.out.println(st.getTextBody());
            System.out.println(st.getHtmlBody());
        }
    }
}
