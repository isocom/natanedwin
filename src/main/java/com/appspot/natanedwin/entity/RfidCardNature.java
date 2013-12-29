package com.appspot.natanedwin.entity;

/**
 *
 * @author prokob01
 */
public enum RfidCardNature {

    Regular, Kindergarten;

    @Override
    public String toString() {
        switch (this) {
            case Regular:
                return "Normalna";
            case Kindergarten:
                return "Przedszkolna";
            default:
                return name();
        }
    }

}
