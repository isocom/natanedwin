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

    public static void fixResDruk1(PrintWriter writer) {
        EstablishmentDao dao = SpringContext.INSTANCE.getBean(EstablishmentDao.class);
        Establishment establishment = new Establishment();

        establishment.setName("RS DRUK Sp. z o.o.");

        //Elżbieta Świątoniowska
        establishment.getHumans().add(Ref.create(Key.create(Human.class, 2289001)));
        //Ryszard Świątoniowski
        establishment.getHumans().add(Ref.create(Key.create(Human.class, 2299001)));
        //Renata Augustyniak
        establishment.getHumans().add(Ref.create(Key.create(Human.class, 2309001)));
        //Marta Czernicka
        establishment.getHumans().add(Ref.create(Key.create(Human.class, 2319001)));
        //PRACOWNIK 1
        establishment.getHumans().add(Ref.create(Key.create(Human.class, 2329001)));
        //Bartłomiej Bałchan
        establishment.getHumans().add(Ref.create(Key.create(Human.class, 2339001)));
        //Elzbieta Korbecka
        establishment.getHumans().add(Ref.create(Key.create(Human.class, 2349001)));
        //Andrzej Łoboś
        establishment.getHumans().add(Ref.create(Key.create(Human.class, 2359001)));
        //Piotr Małysiak
        establishment.getHumans().add(Ref.create(Key.create(Human.class, 2369001)));
        //Beata Pasternak
        establishment.getHumans().add(Ref.create(Key.create(Human.class, 2369002)));
        //Joanna Różewicz
        establishment.getHumans().add(Ref.create(Key.create(Human.class, 2329002)));
        //Jerzy Pasternak
        establishment.getHumans().add(Ref.create(Key.create(Human.class, 2379001)));
        //Marcin Pitucha
        establishment.getHumans().add(Ref.create(Key.create(Human.class, 2299002)));
        //Dariusz Różewicz
        establishment.getHumans().add(Ref.create(Key.create(Human.class, 2289002)));
        //Piotr Różewicz
        establishment.getHumans().add(Ref.create(Key.create(Human.class, 2379002)));
        //Artur Uszycki
        establishment.getHumans().add(Ref.create(Key.create(Human.class, 2389001)));
        //Wilk Małgorzata
        establishment.getHumans().add(Ref.create(Key.create(Human.class, 2309002)));
        //PRACOWNIK 2
        establishment.getHumans().add(Ref.create(Key.create(Human.class, 2299004)));

        dao.save(establishment);
        writer.println(establishment);
    }
}
