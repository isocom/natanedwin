package com.appspot.natanedwin.dao;

import com.appspot.natanedwin.app.AppError;
import com.appspot.natanedwin.entity.Device;
import com.appspot.natanedwin.service.memcache.MemCache;
import com.appspot.natanedwin.service.ofy.Ofy;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.cmd.Query;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author prokob01
 */
@Repository
public class DeviceDao implements Dao<Device> {

    @Autowired
    private Ofy ofy;
    @Autowired
    private MemCache memCache;
    private static final String MC_SN_PREFIX = "Device-SN-";

    @Override
    public Device byId(long id) {
        return ofy.ofy().load().type(Device.class).id(id).safe();
    }

    @Override
    public Device byRef(Ref<Device> ref) {
        return ofy.ofy().load().now(ref.getKey());
    }

    public Device byUUID(final UUID uuid) {
        return byUUID(uuid.toString());
    }

    public Device byUUID(final String uuid) {
        return ofy.ofy().load().type(Device.class).filter("uuid = ", uuid).first().now();
    }

    public List<Device> findAll() {
        Query<Device> query = ofy.ofy().load().type(Device.class);
        List<Device> list = query.list();
        ofy.ofy().clear();
        return list;
    }

    public Device findBySerialNumber(String serialNumber) {
        Device retVal = memCache.get(MC_SN_PREFIX + serialNumber, Device.class);
        if (retVal != null) {
            return retVal;
        }
        retVal = ofy.ofy().load().type(Device.class).filter("serialNumber = ", serialNumber).first().now();
        return retVal;
    }

    @Override
    public void delete(long id) {
        ofy.ofy().delete().type(Device.class).id(id);
    }

    @Override
    public Device delete(Device entity) {
        throw new AppError("Can't delete " + entity.getClass().getSimpleName(), "Nie można usuwać tego typu obiektów");
    }

    @Override
    public Device save(Device device) {
        ofy.ofy().save().entity(device).now();
        memCache.put(MC_SN_PREFIX + device.getSerialNumber(), device);
        return device;
    }
}
