package com.appspot.natanedwin.api;

import com.appspot.natanedwin.dao.EstablishmentDao;
import com.appspot.natanedwin.dao.FiscalPrinterDocumentDao;
import com.appspot.natanedwin.entity.Establishment;
import com.appspot.natanedwin.entity.FiscalPrinterDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/fps")
public class FiscalPrinterController {

    @Autowired
    private EstablishmentDao establishmentDao;
    @Autowired
    private FiscalPrinterDocumentDao fiscalPrinterDocumentDao;

    @RequestMapping(value = "/postDocument", method = RequestMethod.POST, produces = "text/plain; charset=utf-8")
    @ResponseBody
    public String addDocument(@RequestHeader(value = "API-Key", required = true) String apikey,
            @RequestBody String document) {
        Establishment establishment = establishmentDao.byUUID(apikey);
        if (establishment == null) {
            throw new ResourceNotFoundException();
        }

        FiscalPrinterDocument fpd = new FiscalPrinterDocument();
        fpd.setEstablishment(establishment);
        fpd.setDocument(document);
        fiscalPrinterDocumentDao.save(fpd);
        return fpd.getUuid();
    }

    @RequestMapping(value = "/getDocument", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String retrieveDocument(@RequestHeader(value = "API-Key", required = true) String apikey,
            @RequestParam(value = "documentId") String documentId) {
        return fiscalPrinterDocumentDao.byUUID(documentId).getDocument();
    }

    @RequestMapping(value = "/getPrintStatus", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String retrieveStatus(@RequestHeader(value = "API-Key", required = true) String apikey,
            @RequestParam(value = "documentId") String documentId) {
        return fiscalPrinterDocumentDao.byUUID(documentId).getStatus();
    }
}
