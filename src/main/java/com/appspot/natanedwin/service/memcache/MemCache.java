package com.appspot.natanedwin.service.memcache;

/**
 *
 * @author prokob01
 */
public interface MemCache {

    public Object get(Object key);

    public <T> T get(String key, Class<T> clazz);

    public void put(Object key, Object val);
}
