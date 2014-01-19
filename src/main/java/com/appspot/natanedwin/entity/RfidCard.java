package com.appspot.natanedwin.entity;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Unindex;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 *
 * @author prokob01
 */
@Entity
@Cache
public class RfidCard implements Serializable {

    @Id
    private Long id;
    @Index
    private String uuid = UUID.randomUUID().toString();
    @Unindex
    private RfidCardType rfidCardType;
    @Unindex
    private RfidCardNature rfidCardNature = RfidCardNature.Regular;
    @Index
    private String serialNumber;
    @Unindex
    private Date firstTimeSeen = new Date();
    @Index
    private String cardNumber;
    @Index
    private Ref<Human> human;
    @Unindex
    private Ref<GcsFile> overprint;
    @Unindex
    private String remarks;

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
        if (obj != null && obj instanceof RfidCard) {
            return uuid.equalsIgnoreCase(((RfidCard) obj).uuid);
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
    public void setHuman(Human human) {
        this.human = Ref.create(Key.create(Human.class, human.getId()));
    }

    public void setHuman(long id) {
        this.human = Ref.create(Key.create(Human.class, id));
    }

    public Human getHumanChecked() {
        if (this.human == null) {
            return null;
        }
        return this.human.get();
    }

    public void setOverprint(GcsFile file) {
        this.overprint = Ref.create(Key.create(GcsFile.class, file.getId()));
    }

    public void setOverprint(long id) {
        this.overprint = Ref.create(Key.create(GcsFile.class, id));
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

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public RfidCardType getRfidCardType() {
        return rfidCardType;
    }

    public void setRfidCardType(RfidCardType rfidCardType) {
        this.rfidCardType = rfidCardType;
    }

    public RfidCardNature getRfidCardNature() {
        return rfidCardNature;
    }

    public void setRfidCardNature(RfidCardNature rfidCardNature) {
        this.rfidCardNature = rfidCardNature;
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

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Ref<Human> getHuman() {
        return human;
    }

    public void setHuman(Ref<Human> human) {
        this.human = human;
    }

    public Ref<GcsFile> getOverprint() {
        return overprint;
    }

    public void setOverprint(Ref<GcsFile> overprint) {
        this.overprint = overprint;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
