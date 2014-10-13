package com.appspot.natanedwin.entity;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Unindex;
import com.googlecode.objectify.condition.IfNotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author prokob01
 */
@Entity
public class Establishment implements Serializable {

    @Id
    private Long id;
    @Index
    private String uuid = UUID.randomUUID().toString();
    @Index
    private String name;
    @Unindex
    private Date licenseValidity = new Date();
    @Index(IfNotNull.class)
    private String plNip;
    private List<Ref<Human>> humans = new ArrayList<>();
    @Unindex
    private String configuration = "{}";
    ////////////////////////////////////////////////////////////////////////////
    // Standard EQUALLS and HASHCODE ///////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && obj instanceof Establishment) {
            return uuid.equalsIgnoreCase(((Establishment) obj).uuid);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }

    @Override
    public String toString() {
        return name;
    }

    ////////////////////////////////////////////////////////////////////////////
    // HIDING REF<?> ///////////////////////////////////////////////////////////    
    ////////////////////////////////////////////////////////////////////////////
    public void addHuman(Human human) {
        Ref<Human> h = Ref.create(Key.create(Human.class, human.getId()));
        getHumans().add(h);
    }

    public List<Human> safeHumans() {
        List<Human> hh = new ArrayList<>();
        for (Ref<Human> hr : humans) {
            hh.add(hr.safe());
        }
        Collections.sort(hh);
        return hh;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getLicenseValidity() {
        return licenseValidity;
    }

    public void setLicenseValidity(Date licenseValidity) {
        this.licenseValidity = licenseValidity;
    }

    public String getPlNip() {
        return plNip;
    }

    public void setPlNip(String plNip) {
        this.plNip = plNip;
    }

    public List<Ref<Human>> getHumans() {
        return humans;
    }

    public void setHumans(List<Ref<Human>> humans) {
        this.humans = humans;
    }

    public String getConfiguration() {
        return configuration;
    }

    public void setConfiguration(String configuration) {
        this.configuration = configuration;
    }
}
