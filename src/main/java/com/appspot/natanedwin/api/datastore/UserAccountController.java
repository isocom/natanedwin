package com.appspot.natanedwin.api.datastore;

import com.appspot.natanedwin.dao.UserAccountDao;
import com.appspot.natanedwin.entity.UserAccount;
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
public class UserAccountController {
    @Autowired
    private UserAccountDao dao;

    @RequestMapping(value = "/datastore/UserAccount/byId/{id}")
    @ResponseBody
    public UserAccount byId(@PathVariable long id) {
        return dao.byId(id);
    }
}
