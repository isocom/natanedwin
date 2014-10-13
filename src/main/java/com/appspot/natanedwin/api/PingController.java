package com.appspot.natanedwin.api;

import com.appspot.natanedwin.dao.EstablishmentDao;
import com.appspot.natanedwin.entity.Establishment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PingController {
    
    @Autowired
    private EstablishmentDao establishmentDao;
    
    @RequestMapping("/ping")
    @ResponseBody
    public String ping(@RequestHeader(value = "API-Key", required = false) String apikey) {
        StringBuilder sb = new StringBuilder();
        sb.append("pong\n");
        sb.append("API-Key = ").append(apikey).append('\n');
        Establishment establishment = establishmentDao.byUUID(apikey);
        if (establishment != null) {
            sb.append("Licencjobiorca: ").append(establishment.getName()).append('\n');
        }
        return sb.toString();
    }
}
