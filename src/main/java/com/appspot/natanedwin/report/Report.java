package com.appspot.natanedwin.report;

public interface Report {

    public String asHTML();

    public byte[] asPDF();
}
