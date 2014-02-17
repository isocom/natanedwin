/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appspot.natanedwin.entity;

import com.google.appengine.api.datastore.Text;
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
public class StatEntry implements Serializable {

    @Id
    private Long id;
    @Index
    private Date date = new Date();
    @Unindex
    private Text jsonData;

    ////////////////////////////////////////////////////////////////////////////
    // Standard EQUALLS and HASHCODE ///////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    @Override
    public boolean equals(Object obj) {
        if (id == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (obj != null && obj instanceof StatEntry) {
            return id.equals(((StatEntry) obj).getId());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        if (id != null) {
            return id.hashCode();
        } else {
            return 0;
        }
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Text getJsonData() {
        return jsonData;
    }

    public void setJsonData(Text jsonData) {
        this.jsonData = jsonData;
    }
}
