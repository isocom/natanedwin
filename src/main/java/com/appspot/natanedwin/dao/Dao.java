package com.appspot.natanedwin.dao;

/**
 *
 * @author prokob01
 * @param <E>
 */
public interface Dao<E> {

    public E byId(long id);

    public E delete(E entity);

    public E save(E entity);
}
