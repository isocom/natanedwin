package com.appspot.natanedwin.dao;

/**
 *
 * @author prokob01
 */
public interface Dao<E> {

    public E byId(long id);

    public void delete(E entity);

    public void save(E entity);
}
