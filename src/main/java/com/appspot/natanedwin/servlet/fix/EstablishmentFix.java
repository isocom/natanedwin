package com.appspot.natanedwin.servlet.fix;

import com.appspot.natanedwin.dao.EstablishmentDao;
import com.appspot.natanedwin.entity.Establishment;
import com.appspot.natanedwin.entity.Human;
import com.appspot.natanedwin.service.spring.SpringContext;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import java.io.PrintWriter;

public class EstablishmentFix {

    public static void fixReSave(PrintWriter writer) {
        EstablishmentDao dao = SpringContext.INSTANCE.getBean(EstablishmentDao.class);
        for (Establishment h : dao.findAll()) {
            dao.save(h);
            writer.println("Zapisano: " + h + " | " + h.getId());
        }
    }

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

        establishment.setName("RS DRUK Pracownia");

        //Bakalarczyk Piotr
        establishment.getHumans().add(Ref.create(Key.create(Human.class, 2359002)));
        //Fejkiel Mariusz
        establishment.getHumans().add(Ref.create(Key.create(Human.class, 2379003)));
        //Matusz Jaromir
        establishment.getHumans().add(Ref.create(Key.create(Human.class, 2339002)));
        //Mordarski Krzysztof
        establishment.getHumans().add(Ref.create(Key.create(Human.class, 2369003)));
        //Nicgorski Piotr
        establishment.getHumans().add(Ref.create(Key.create(Human.class, 2399002)));
        //Nicgorska Urszula
        establishment.getHumans().add(Ref.create(Key.create(Human.class, 2349002)));
        //Nowak Dawid
        establishment.getHumans().add(Ref.create(Key.create(Human.class, 2299003)));
        //Pecuch Arkadiusz
        establishment.getHumans().add(Ref.create(Key.create(Human.class, 2319002)));
        //Rzucidło Lilla
        establishment.getHumans().add(Ref.create(Key.create(Human.class, 2409001)));
        //Świątoniowska-Kret Monika
        establishment.getHumans().add(Ref.create(Key.create(Human.class, 2339003)));
        //Wilk Grażyna
        establishment.getHumans().add(Ref.create(Key.create(Human.class, 2419001)));
        //Wójtowicz Grzegorz
        establishment.getHumans().add(Ref.create(Key.create(Human.class, 2289003)));
        //Żyga Sabina
        establishment.getHumans().add(Ref.create(Key.create(Human.class, 2389002)));
        //Stochła Renata
        establishment.getHumans().add(Ref.create(Key.create(Human.class, 2399003)));
        //Pyziak Tadeusz
        establishment.getHumans().add(Ref.create(Key.create(Human.class, 2349003)));
        //Grzegorz Lenartowicz
        establishment.getHumans().add(Ref.create(Key.create(Human.class, 2349004)));
        //Józefczyk Ewa
        establishment.getHumans().add(Ref.create(Key.create(Human.class, 2429001)));
        //Kustroń Mateusz
        establishment.getHumans().add(Ref.create(Key.create(Human.class, 2319003)));
        //PRACOWNIK 1
        establishment.getHumans().add(Ref.create(Key.create(Human.class, 2419002)));
        //PRACOWNIK 2
        establishment.getHumans().add(Ref.create(Key.create(Human.class, 2399001)));

        dao.save(establishment);
        writer.println(establishment);
    }
}
