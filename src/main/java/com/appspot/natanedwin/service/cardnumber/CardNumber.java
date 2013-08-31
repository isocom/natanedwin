/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appspot.natanedwin.service.cardnumber;

/**
 *
 * @author prokob01
 */
public interface CardNumber {

    public boolean isValidLuhnNumber(String s);

    public String compress(String ccn);

    public String pretty(String ccn);

    public String generate();
}
