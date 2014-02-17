package com.appspot.natanedwin.api.datastore;

import com.appspot.natanedwin.dao.HumanDao;
import com.appspot.natanedwin.entity.Human;
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
public class HumanController {

    @Autowired
    private HumanDao dao;

    @RequestMapping(value = "/datastore/Human/byId/{id}")
    @ResponseBody
    public Human byId(@PathVariable long id) {
        return dao.byId(id);
    }
}
