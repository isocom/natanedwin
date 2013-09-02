package com.appspot.natanedwin.report;

import java.io.Serializable;

public interface Report extends Serializable {

    public String getFileName();
    
    public String asHTML();

    public ByteArrayStreamResource asPDF();

    public ByteArrayStreamResource asXLS();
}
