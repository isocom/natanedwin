package com.appspot.natanedwin.api.datastore;

import com.appspot.natanedwin.dao.DeviceDao;
import com.appspot.natanedwin.entity.Device;
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
public class DeviceController {

    @Autowired
    private DeviceDao dao;

    @RequestMapping(value = "/datastore/Device/byId/{id}")
    @ResponseBody
    public Device byId(@PathVariable long id) {
        return dao.byId(id);
    }
}
