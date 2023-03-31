package com.rat.ratatouille23.eccezioni;

public class PiattoNonAggiuntoException extends PersonalizzaMenuException {

    private static String messaggioDefault = "Non é stato possibile aggiungere il piatto";

    public PiattoNonAggiuntoException(String s) {
        super(s);
    }

    public PiattoNonAggiuntoException() {
        super(messaggioDefault);
    }
}
