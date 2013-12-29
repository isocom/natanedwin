/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appspot.natanedwin.service.cardnumber;

import java.util.Random;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

/**
 *
 * @author prokob01
 */
@Service
public class CardNumberImpl implements CardNumber {

    /**
     * We use pseudo-random generator to generate card numbers.
     */
    private final static Random R = new Random();
    /**
     * Major Industry Identifier (MII)
     *
     * The first digit of a credit card number is the Major Industry Identifier
     * (MII), which represents the category of entity which issued the credit
     * card. Different MII digits represent the following issuer categories:
     * <pre>
     * 0. ISO/TC 68 and other future industry assignments
     * 1. Airlines
     * 2. Airlines and other future industry assignments
     * 3. Travel and entertainment and banking/financial
     * 4. Banking and financial
     * 5. Banking and financial
     * 6. Merchandising and banking/financial
     * 7. Petroleum and other future industry assignments
     * 8. Healthcare, telecommunications and other future industry assignments
     * 9. National assignment
     * </pre> For example, American Express, Diner's Club, and Carte Blanche are
     * in the travel and entertainment category, VISA, MasterCard, and Discover
     * are in the banking and financial category, and Sun Oil and Exxon are in
     * the petroleum category.
     *
     */
    private final static String MII = "9";
    /**
     * ISO 3166-1 is part of the ISO 3166 standard published by the
     * International Organization for Standardization (ISO), and defines codes
     * for the names of countries, dependent territories, and special areas of
     * geographical interest. The official name of the standard is Codes for the
     * representation of names of countries and their subdivisions 鈥?Part 1:
     * Country codes. It defines three sets of country codes:[1] ISO 3166-1
     * alpha-2 鈥?two-letter country codes which are the most widely used of the
     * three, and used most prominently for the Internet's country code
     * top-level domains (with a few exceptions). ISO 3166-1 alpha-3
     * 鈥?three-letter country codes which allow a better visual association
     * between the codes and the country names than the alpha-2 codes. ISO
     * 3166-1 numeric 鈥?three-digit country codes which are identical to those
     * developed and maintained by the United Nations Statistics Division, with
     * the advantage of script (writing system) independence, and hence useful
     * for people or systems using non-Latin scripts.
     *
     * The alphabetic country codes were first included in ISO 3166 in 1974, and
     * the numeric country codes were first included in 1981. The country codes
     * have been published as ISO 3166-1 since 1997, when ISO 3166 was expanded
     * into three parts to include codes for subdivisions and former
     * countries.[2]
     *
     */
    private final static String PL_ISO3166_1_PL = "616";
    /**
     * Token - always between 10 and 89
     */
    private static int token = 9 + R.nextInt(81);

    /**
     * Issuer identifier number (IIN)
     *
     * The first six digits, including the major industry identifier, compose
     * the issuer identifier number (IIN). This identifies the issuing
     * organization. The American Bankers Association is the registration
     * authority for IINs. The official ISO registry of IINs, the "ISO Register
     * of Card Issuer Identification Numbers", is not available to the general
     * public. It is only available to institutions which hold IINs, issue
     * plastic cards, or act as a financial network or processor. Institutions
     * in the third category must sign a license agreement before they are given
     * access to the registry. Several IINs are well known, especially those
     * representing credit card issuers.
     * 
     * @return Prefix of the card number
     */
    protected static synchronized String buildIIN() {
        token++;
        if (token >= 90) {
            token = 10;
        }
        return MII + PL_ISO3166_1_PL + token;
    }

    @Override
    public String generate() {
        return generateProposal();
    }

    protected String generateProposal() {
        String n = R.nextInt(1000000000) + "";
        while (n.length() < 9) {
            n = "0" + n;
        }
        String retVal = buildIIN() + n; // first 6 numbers
        return pretty(retVal + generateLuhnDigit(retVal));
    }

    /**
     * Calculates Luhn digit for specified number code
     *
     * @param s number code to be used to generate Luhn digits. All digits are
     * used for calculation from 0 to length()-1.
     * @return Luhn number as String. In fact it is String[1] containing single
     * decimal digit
     */
    public String generateLuhnDigit(String s) {
        int digit = calcLuhn(s, true) % 10;
        if (digit != 0) {
            digit = 10 - digit;
        }
        return "" + digit;
    }

    /**
     * Checks if given string is passes Luhn test. We assume that last digit
     * contains Luhn digit.
     *
     * @param s number code to be tested
     * @return true if Luhn test was passed, false otherwise
     */
    @Override
    public boolean isValidLuhnNumber(String s) {
        s = compress(s);
        return calcLuhn(s, false) % 10 == 0;
    }

    /**
     * performs necessary calculation to calculate Luhn digit
     *
     * @param numberCode
     * @param even - if rightmost position is treated as odd or even. It is nice
     * hack to use the same function for both generating and validating Luhn
     * digit
     * @return Luhn digit
     */
    private int calcLuhn(String numberCode, boolean even) {
        int luhnSum = 0;
        for (int i = numberCode.length() - 1; i >= 0; i--) {
            int n = Integer.parseInt(numberCode.substring(i, i + 1));
            if (even) {
                n *= 2;
                if (n > 9) {
                    n = (n % 10) + 1;
                }
            }
            luhnSum += n;
            even = !even;
        }

        return luhnSum;
    }

    @Override
    public String compress(String ccn) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ccn.length(); i++) {
            if (Character.isDigit(ccn.charAt(i))) {
                sb.append(ccn.charAt(i));
            }
        }
        return sb.toString();
    }

    @Override
    public String pretty(String ccn) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ccn.length(); i++) {
            char c = ccn.charAt(i);
            if (!Character.isDigit(c)) {
                continue;
            }
            sb.append(c);
            if (i % 4 == 3) {
                sb.append(" ");
            }
        }
        return sb.toString().trim();
    }

    public static void main(String... args) {
        CardNumberImpl cardNumberImpl = new CardNumberImpl();
        String generated = cardNumberImpl.generate();
        System.out.println(generated + " : " + cardNumberImpl.isValidLuhnNumber(generated));
        System.out.println(cardNumberImpl.generate());
        System.out.println(cardNumberImpl.generate());
        System.out.println(cardNumberImpl.generate());
        System.out.println(RandomStringUtils.randomAlphanumeric(40));
        System.out.println(RandomStringUtils.randomAlphanumeric(100));
    }
}
