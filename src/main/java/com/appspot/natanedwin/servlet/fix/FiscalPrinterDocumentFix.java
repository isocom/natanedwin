package com.appspot.natanedwin.servlet.fix;

import com.appspot.natanedwin.dao.FiscalPrinterDocumentDao;
import com.appspot.natanedwin.entity.FiscalPrinterDocument;
import com.appspot.natanedwin.service.spring.SpringContext;
import java.io.PrintWriter;

public class FiscalPrinterDocumentFix {

    public static void fixReSave(PrintWriter writer) {
        FiscalPrinterDocumentDao dao = SpringContext.INSTANCE.getBean(FiscalPrinterDocumentDao.class);
        for (FiscalPrinterDocument h : dao.findAll()) {
            dao.save(h);
            writer.println("Zapisano: " + h + " | " + h.getId());
        }
    }

}
