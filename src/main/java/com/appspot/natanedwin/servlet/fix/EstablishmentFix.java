package com.appspot.natanedwin.servlet.fix;

import com.appspot.natanedwin.dao.EstablishmentDao;
import com.appspot.natanedwin.entity.Establishment;
import com.appspot.natanedwin.entity.Human;
import com.appspot.natanedwin.service.spring.SpringContext;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import java.io.PrintWriter;

public class EstablishmentFix {

    public static void fixMetalsprzet(PrintWriter writer) {
        EstablishmentDao dao = SpringContext.INSTANCE.getBean(EstablishmentDao.class);
        Establishment establishment = new Establishment();
        establishment.setName("Metalsprzęt");
        establishment.getHumans().add(Ref.create(Key.create(Human.class, 311002)));
        establishment.getHumans().add(Ref.create(Key.create(Human.class, 312002)));
        establishment.getHumans().add(Ref.create(Key.create(Human.class, 312003)));
        establishment.getHumans().add(Ref.create(Key.create(Human.class, 313002)));
        establishment.getHumans().add(Ref.create(Key.create(Human.class, 313003)));
        establishment.getHumans().add(Ref.create(Key.create(Human.class, 313004)));
        establishment.getHumans().add(Ref.create(Key.create(Human.class, 313005)));
        establishment.getHumans().add(Ref.create(Key.create(Human.class, 313006)));
        establishment.getHumans().add(Ref.create(Key.create(Human.class, 314001)));
        establishment.getHumans().add(Ref.create(Key.create(Human.class, 314002)));
        establishment.getHumans().add(Ref.create(Key.create(Human.class, 314003)));
        establishment.getHumans().add(Ref.create(Key.create(Human.class, 315001)));
        establishment.getHumans().add(Ref.create(Key.create(Human.class, 315002)));
        establishment.getHumans().add(Ref.create(Key.create(Human.class, 315003)));
        establishment.getHumans().add(Ref.create(Key.create(Human.class, 316001)));
        establishment.getHumans().add(Ref.create(Key.create(Human.class, 316002)));
        establishment.getHumans().add(Ref.create(Key.create(Human.class, 316003)));
        establishment.getHumans().add(Ref.create(Key.create(Human.class, 317001)));
        establishment.getHumans().add(Ref.create(Key.create(Human.class, 318001)));
        establishment.getHumans().add(Ref.create(Key.create(Human.class, 318002)));
        establishment.getHumans().add(Ref.create(Key.create(Human.class, 318003)));
        establishment.getHumans().add(Ref.create(Key.create(Human.class, 319001)));
        establishment.getHumans().add(Ref.create(Key.create(Human.class, 319002)));
        establishment.getHumans().add(Ref.create(Key.create(Human.class, 319003)));
        establishment.getHumans().add(Ref.create(Key.create(Human.class, 320001)));
        establishment.getHumans().add(Ref.create(Key.create(Human.class, 321001)));
        establishment.getHumans().add(Ref.create(Key.create(Human.class, 322001)));
        establishment.getHumans().add(Ref.create(Key.create(Human.class, 322002)));
        establishment.getHumans().add(Ref.create(Key.create(Human.class, 323001)));
        establishment.getHumans().add(Ref.create(Key.create(Human.class, 324001)));
        establishment.getHumans().add(Ref.create(Key.create(Human.class, 324002)));
        establishment.getHumans().add(Ref.create(Key.create(Human.class, 324003)));
        establishment.getHumans().add(Ref.create(Key.create(Human.class, 325001)));
        establishment.getHumans().add(Ref.create(Key.create(Human.class, 325002)));
        establishment.getHumans().add(Ref.create(Key.create(Human.class, 325003)));
        establishment.getHumans().add(Ref.create(Key.create(Human.class, 325004)));
        establishment.getHumans().add(Ref.create(Key.create(Human.class, 326001)));
        establishment.getHumans().add(Ref.create(Key.create(Human.class, 327001)));
        establishment.getHumans().add(Ref.create(Key.create(Human.class, 397001)));
        establishment.getHumans().add(Ref.create(Key.create(Human.class, 398001)));
        establishment.getHumans().add(Ref.create(Key.create(Human.class, 399001)));
        establishment.getHumans().add(Ref.create(Key.create(Human.class, 1152001)));
        establishment.getHumans().add(Ref.create(Key.create(Human.class, 1290007)));
        dao.save(establishment);
        writer.println(establishment);
    }
}
