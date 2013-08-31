/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appspot.natanedwin.service.memcache;

import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import org.springframework.stereotype.Service;

/**
 *
 * @author prokob01
 */
@Service
public class MemCacheImpl implements MemCache {

    private MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
    //private AsyncMemcacheService asyncCache = MemcacheServiceFactory.getAsyncMemcacheService();

    public MemCacheImpl() {
    }

    @Override
    public Object get(Object key) {
        return syncCache.get(key);
    }

    @Override
    public <T> T get(String key, Class<T> clazz) {
        return (T) syncCache.get(key);
    }

    @Override
    public void put(Object key, Object val) {
        syncCache.put(key, val);
    }
}
