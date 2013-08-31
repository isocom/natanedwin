package com.appspot.natanedwin.entity;

import com.google.appengine.api.datastore.Email;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Unindex;
import java.io.Serializable;
import java.util.UUID;

/**
 *
 * @author prokob01
 */
@Entity
public class UserAccount implements Serializable {

    @Id
    private Long id;
    @Index
    private String uuid = UUID.randomUUID().toString();
    private UserAccountType userAccountType;
    @Index
    private String userId;
    private String passwordHash;
    private Email email;
    @Unindex
    private String dateTimeZone = "Europe/Warsaw";
    @Unindex
    private String locale = "pl_PL";

    @Override
    public String toString() {
        return getClass() + ":" + id + "/" + userId + "/" + email;
    }

    ////////////////////////////////////////////////////////////////////////////
    // Standard EQUALLS and HASHCODE ///////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && obj instanceof UserAccount) {
            return uuid.equalsIgnoreCase(((UserAccount) obj).uuid);
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

    public UserAccountType getUserAccountType() {
        return userAccountType;
    }

    public void setUserAccountType(UserAccountType userAccountType) {
        this.userAccountType = userAccountType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public void setEmail(String email) {
        this.email = new Email(email);
    }

    public String getDateTimeZone() {
        return dateTimeZone;
    }

    public void setDateTimeZone(String dateTimeZone) {
        this.dateTimeZone = dateTimeZone;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }
}
