package com.appspot.natanedwin.entity;

/**
 *
 * @author prokob01
 */
public enum RfidEventType {

    In, Out, TempIn, TempOut;

    @Override
    public String toString() {
        switch (this) {
            case In:
                return "Rozpoczęcie";
            case Out:
                return "Zakończenie";
            case TempIn:
                return "Powrót";
            case TempOut:
                return "Wyjście";
            default:
                return name();
        }
    }

}
