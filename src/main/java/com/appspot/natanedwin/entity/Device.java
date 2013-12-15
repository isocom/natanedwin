package com.appspot.natanedwin.entity;

import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Unindex;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author prokob01
 */
@Entity
@Cache
public class Device implements Serializable {

    @Id
    private Long id = null;
    @Index
    private String uuid = UUID.randomUUID().toString();
    @Unindex
    private DeviceType deviceType = null;
    @Index
    private String serialNumber = null;
    @Unindex
    private Date firstTimeSeen = new Date();
    @Unindex
    private String description = "";
    @Unindex
    private String configuration = "{}";

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
    ////////////////////////////////////////////////////////////////////////////
    // Standard EQUALLS and HASHCODE ///////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && obj instanceof Device) {
            return uuid.equalsIgnoreCase(((Device) obj).uuid);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }
    ////////////////////////////////////////////////////////////////////////////
    // HIDING REF<?> ///////////////////////////////////////////////////////////    
    ////////////////////////////////////////////////////////////////////////////

    public JSONObject getJsonConfiguration() {
        try {
            return new JSONObject(configuration);
        } catch (JSONException jsone) {
            throw new RuntimeException(jsone);
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    // GETTERS AND SETTERS
    ////////////////////////////////////////////////////////////////////////////
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public DeviceType getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Date getFirstTimeSeen() {
        return firstTimeSeen;
    }

    public void setFirstTimeSeen(Date firstTimeSeen) {
        this.firstTimeSeen = firstTimeSeen;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getConfiguration() {
        return configuration;
    }

    public void setConfiguration(String configuration) {
        this.configuration = configuration;
    }
}
