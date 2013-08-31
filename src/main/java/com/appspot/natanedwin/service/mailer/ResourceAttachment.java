/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appspot.natanedwin.service.mailer;

/**
 *
 * @author prokob01
 */
public class ResourceAttachment extends URLAttachment {

    public ResourceAttachment(String mimeType, String cid, String resourcePath) {
        super(mimeType, cid, ResourceAttachment.class.getResource(resourcePath));
    }
}
