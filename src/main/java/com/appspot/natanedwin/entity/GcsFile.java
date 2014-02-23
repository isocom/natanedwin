package com.appspot.natanedwin.entity;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Unindex;
import java.io.Serializable;
import java.util.UUID;

@Entity
public class GcsFile implements Serializable {

    @Id
    private Long id;
    @Index
    private String uuid = UUID.randomUUID().toString();
    @Index
    private String bucketName = "natanedwin";
    @Index
    private String objectName;
    @Index
    private String md5sum;
    @Index
    private String sha1sum;
    @Unindex
    private String description = null;
    ////////////////////////////////////////////////////////////////////////////
    // Standard EQUALLS and HASHCODE ///////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && obj instanceof GcsFile) {
            return uuid.equalsIgnoreCase(((GcsFile) obj).uuid);
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

    public String getMd5sum() {
        return md5sum;
    }

    public void setMd5sum(String md5sum) {
        this.md5sum = md5sum;
    }

    public String getSha1sum() {
        return sha1sum;
    }

    public void setSha1sum(String sha1sum) {
        this.sha1sum = sha1sum;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
