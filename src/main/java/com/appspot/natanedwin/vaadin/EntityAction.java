/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appspot.natanedwin.vaadin;

import java.io.Serializable;

/**
 *
 * @author rr163240
 */
public interface EntityAction<ENTITY> extends Serializable {

    public void execute(ENTITY entity);
}
