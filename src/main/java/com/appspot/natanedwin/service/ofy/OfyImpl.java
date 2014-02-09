package com.appspot.natanedwin.service.ofy;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Service;

/**
 *
 * @author prokob01
 */
@Service
public class OfyImpl implements Ofy {

    @PostConstruct
    private void construct() {
        factory().register(com.appspot.natanedwin.entity.Device.class);
        factory().register(com.appspot.natanedwin.entity.Establishment.class);
        factory().register(com.appspot.natanedwin.entity.GcsFile.class);
        factory().register(com.appspot.natanedwin.entity.Human.class);
        factory().register(com.appspot.natanedwin.entity.RfidCard.class);
        factory().register(com.appspot.natanedwin.entity.RfidEvent.class);
        factory().register(com.appspot.natanedwin.entity.StatEntry.class);
        factory().register(com.appspot.natanedwin.entity.UserAccount.class);
    }

    private ObjectifyFactory factory() {
        return ObjectifyService.factory();
    }

    @Override
    public Objectify ofy() {
        return ObjectifyService.ofy();
    }
}
