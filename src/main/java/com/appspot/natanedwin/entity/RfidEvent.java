package com.appspot.natanedwin.entity;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Unindex;
import com.googlecode.objectify.condition.IfNotNull;
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
    @Index(IfNotNull.class)
    private Ref<Device> device;
    @Index(IfNotNull.class)
    private Ref<RfidCard> rfidCard;
    @Index(IfNotNull.class)
    private Ref<Human> human;

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
    public void setDevice(Device device) {
        this.device = Ref.create(device);
    }

    public void setHuman(Key<Human> key) {
        this.human = Ref.create(key);
    }

    public void setHuman(Human human) {
        this.human = Ref.create(Key.create(Human.class, human.getId()));
    }

    public RfidCard safeRfidCard() {
        if (rfidCard == null) {
            return null;
        }
        return rfidCard.safe();
    }

    public Human safeHuman() {
        if (human == null) {
            return null;
        }
        return human.safe();
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

    public Ref<Human> getHuman() {
        return human;
    }

    public void setHuman(Ref<Human> human) {
        this.human = human;
    }

    public Ref<Device> getDevice() {
        return device;
    }

    public void setDevice(Ref<Device> device) {
        this.device = device;
    }

    public Ref<RfidCard> getRfidCard() {
        return rfidCard;
    }

    public void setRfidCard(Ref<RfidCard> rfidCard) {
        this.rfidCard = rfidCard;
    }

}
