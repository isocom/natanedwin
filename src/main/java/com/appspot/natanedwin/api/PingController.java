package com.appspot.natanedwin.api;

import com.appspot.natanedwin.dao.EstablishmentDao;
import com.appspot.natanedwin.entity.Establishment;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/ping")
public class PingController {

    @Autowired
    private EstablishmentDao establishmentDao;

    /**
     * Example: curl
     *
     * @param apikey
     * @return
     */
    @ResponseBody
    @RequestMapping("/fps")
    public String fps(@RequestHeader(value = "API-Key", required = false) String apikey) {
        StringBuilder sb = new StringBuilder();
        sb.append("pong\n");
        sb.append("API-Key = ").append(apikey).append('\n');
        Establishment establishment = establishmentDao.byUUID(apikey);
        if (establishment != null) {
            sb.append("Licencjobiorca: ").append(establishment.getName()).append('\n');
        }
        return sb.toString();
    }

    private Map<String, String> log = new HashMap<>();

    /**
     * Example:
     * <pre>
     * curl -4 http://21.natanedwin.appspot.com/api/ping/iptrack?key=dev4
     * curl -6 http://21.natanedwin.appspot.com/api/ping/iptrack?key=dev6
     * </pre>
     *
     * @param req
     * @param key
     * @return
     */
    @ResponseBody
    @RequestMapping("/iptrack")
    public String iptrack(HttpServletRequest request, @RequestParam(value = "key", required = false) String key) {
        if (key != null) {
            log.put(key, request.getRemoteAddr() + " = " + request.getRemoteHost());
        }
        StringBuilder retVal = new StringBuilder();
        for (Map.Entry<String, String> e : log.entrySet()) {
            retVal.append(e.getKey());
            retVal.append(": ");
            retVal.append(e.getValue());
            retVal.append('\n');
        }
        return retVal.toString();
    }
}
