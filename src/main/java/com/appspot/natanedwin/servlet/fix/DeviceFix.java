package com.appspot.natanedwin.servlet.fix;

import com.appspot.natanedwin.dao.DeviceDao;
import com.appspot.natanedwin.entity.Device;
import com.appspot.natanedwin.service.spring.SpringContext;
import java.io.PrintWriter;

public class DeviceFix {

    public static void fixReSave(PrintWriter writer) {
        DeviceDao dao = SpringContext.INSTANCE.getBean(DeviceDao.class);
        for (Device h : dao.findAll()) {
            dao.save(h);
            writer.println("Zapisano: " + h + " | " + h.getId());
        }
    }

}
