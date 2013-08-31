/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appspot.natanedwin.entity;

import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import java.io.Serializable;
import java.util.UUID;

/**
 *
 * @author prokob01
 */
@Entity
@Cache
public class Human implements Serializable {

    @Id
    private Long id;
    @Index
    private String uuid = UUID.randomUUID().toString();
    @Index
    private String name;

    ////////////////////////////////////////////////////////////////////////////
    // Standard EQUALLS and HASHCODE ///////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && obj instanceof Human) {
            return uuid.equalsIgnoreCase(((Human) obj).uuid);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
