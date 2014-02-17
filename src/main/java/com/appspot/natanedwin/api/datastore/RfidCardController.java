package com.appspot.natanedwin.api.datastore;

import com.appspot.natanedwin.dao.RfidCardDao;
import com.appspot.natanedwin.entity.RfidCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author prokob01
 */
@Controller
public class RfidCardController {

    @Autowired
    private RfidCardDao dao;

    @RequestMapping(value = "/datastore/RfidCard/byId/{id}")
    @ResponseBody
    public RfidCard byId(@PathVariable long id) {
        return dao.byId(id);
    }
}
