package com.appspot.natanedwin.servlet.tr610;

import com.appspot.natanedwin.entity.Device;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public final class TR610Response {

    private static final String CMD_ESC = "~X";
    private static final String CMD_DEL = "|";
    private static final String CMD_END = CMD_DEL + "`";

    public static void heartBeat1(PrintWriter resp, Device device) {
        SimpleDateFormat dateFormater = new SimpleDateFormat("yyMMdd HHmmss");
        dateFormater.setTimeZone(TimeZone.getTimeZone("CET"));
        resp.println(CMD_ESC + "HeartBeat1" + CMD_DEL + dateFormater.format(new Date()) + CMD_END);
    }

    public static void hello1(PrintWriter resp, Device device) {
        resp.println(CMD_ESC + "Hello1" + CMD_DEL + device.getId() + CMD_END);
    }

    public static void enableLocalTTSoftDevices(PrintWriter resp) {
        resp.println(CMD_ESC + "StartTCPTT" + CMD_END);
    }

    public static void enableLocalRs232Server(PrintWriter resp) {
        resp.println(CMD_ESC + "StartRS232" + CMD_END);
    }

    public static void display1(PrintWriter resp, String line1) {
        line1 = latinize(line1);
        resp.println(CMD_ESC + "Display1" + CMD_DEL + line1 + CMD_END);
    }

    public static void display2(PrintWriter resp, String line1, String line2) {
        line1 = latinize(line1);
        line2 = latinize(line2);
        resp.println(CMD_ESC + "Display2" + CMD_DEL + line1 + CMD_DEL + line2 + CMD_END);
    }

    public static void display3(PrintWriter resp, String line1, String line2, String line3) {
        line1 = latinize(line1);
        line2 = latinize(line2);
        line3 = latinize(line3);
        resp.println(CMD_ESC + "Display3" + CMD_DEL + line1 + CMD_DEL + line2 + CMD_DEL + line3 + CMD_END);
    }

    public static void display4(PrintWriter resp, String line1, String line2, String line3, String line4) {
        line1 = latinize(line1);
        line2 = latinize(line2);
        line3 = latinize(line3);
        line4 = latinize(line4);
        resp.println(CMD_ESC + "Display4" + CMD_DEL + line1 + CMD_DEL + line2 + CMD_DEL + line3 + CMD_DEL + line4 + CMD_END);
    }

    public static void beep(PrintWriter resp, String pattern) {
        resp.println(CMD_ESC + "Beep" + CMD_DEL + pattern + CMD_END);
    }

    public static void ttsoftOpenRelay(PrintWriter resp, int addr) {
        resp.println(CMD_ESC + "TTSoftOpen" + CMD_DEL + addr + CMD_END);
    }

    private static String latinize(String s) {
        StringBuilder sb = new StringBuilder();
        for (char c : s.toCharArray()) {
            switch (c) {
                case 'Ą':
                    c = 'A';
                    break;
                case 'ą':
                    c = 'a';
                    break;
                case 'Ć':
                    c = 'C';
                    break;
                case 'ć':
                    c = 'ć';
                    break;
                case 'Ę':
                    c = 'E';
                    break;
                case 'ę':
                    c = 'ę';
                    break;
                case 'Ł':
                    c = 'L';
                    break;
                case 'ł':
                    c = 'l';
                    break;
                case 'Ń':
                    c = 'N';
                    break;
                case 'ń':
                    c = 'n';
                    break;
                case 'Ó':
                    c = 'o';
                    break;
                case 'ó':
                    c = 'o';
                    break;
                case 'Ś':
                    c = 'S';
                    break;
                case 'ś':
                    c = 's';
                    break;
                case 'Ż':
                    c = 'Z';
                    break;
                case 'ż':
                    c = 'z';
                    break;
                case 'Ź':
                    c = 'z';
                    break;
                case 'ź':
                    c = 'z';
                    break;
                default:
                    break;
            }
            sb.append(c);
        }
        return sb.toString();
    }
}
