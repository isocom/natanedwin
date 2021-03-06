package com.appspot.natanedwin.dao;

import com.googlecode.objectify.Ref;
import java.util.UUID;

/**
 *
 * @author prokob01
 * @param <E>
 */
public interface Dao<E> {

    /**
     *
     * @param id
     * @return
     */
    public E byId(long id);

    /**
     *
     * @param ref
     * @return
     */
    public E byRef(Ref<E> ref);

    public E delete(E entity);

    public void delete(long id);

    public E save(E entity);
}
