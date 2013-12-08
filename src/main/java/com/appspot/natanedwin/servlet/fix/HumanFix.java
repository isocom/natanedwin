package com.appspot.natanedwin.servlet.fix;

import com.appspot.natanedwin.dao.HumanDao;
import com.appspot.natanedwin.entity.Human;
import com.appspot.natanedwin.service.spring.SpringContext;
import java.io.PrintWriter;

public class HumanFix {

    public static void fixReSave(PrintWriter writer) {
        HumanDao dao = SpringContext.INSTANCE.getBean(HumanDao.class);
        for (Human h : dao.findAll()) {
            dao.save(h);
            writer.println("Zapisano: " + h + " | " + h.getId());
        }
    }

}
