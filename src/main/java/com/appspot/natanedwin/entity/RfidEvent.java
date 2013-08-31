/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appspot.natanedwin.entity;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Unindex;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author prokob01
 */
@Entity
public class RfidEvent implements Serializable {

    @Id
    private Long id;
    @Unindex
    private RfidEventType rfidEventType;
    @Index
    private Date eventDate = new Date();
    @Index
    private Ref<Device> device;
    @Index
    private Ref<RfidCard> rfidCard;

    @Override
    public String toString() {
        return getClass() + ":" + id + "/" + rfidEventType + "/" + eventDate + "/" + rfidCard;
    }
    ////////////////////////////////////////////////////////////////////////////
    // Standard EQUALLS and HASHCODE ///////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (id == null) {
            return false;
        }
        if (obj != null && obj instanceof RfidEvent) {
            return id == ((RfidEvent) obj).id;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        if (id == null) {
            return 0;
        } else {
            return id.hashCode();
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    // HIDING REF<?> ///////////////////////////////////////////////////////////    
    ////////////////////////////////////////////////////////////////////////////
    public Device getDevice() {
        return device.get();
    }

    public void setDevice(Device device) {
        this.device = Ref.create(device);
    }

    public RfidCard getRfidCard() {
        return rfidCard.get();
    }

    public void setRfidCard(RfidCard card) {
        this.rfidCard = Ref.create(card);
    }

    ////////////////////////////////////////////////////////////////////////////
    // GETTERS AND SETTERS /////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RfidEventType getRfidEventType() {
        return rfidEventType;
    }

    public void setRfidEventType(RfidEventType rfidEventType) {
        this.rfidEventType = rfidEventType;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }
}
