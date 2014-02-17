package com.appspot.natanedwin.report;

import com.vaadin.data.Container;
import java.io.Serializable;
import java.util.Map;

public interface Report extends Serializable {

    public String getFileName();

    public Map<String, Container> asVaadinData();

    public ByteArrayStreamResource asPDF();

    public ByteArrayStreamResource asXLS();
}
