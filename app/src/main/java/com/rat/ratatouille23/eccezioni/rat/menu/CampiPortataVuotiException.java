package com.rat.ratatouille23.eccezioni.rat.menu;

public class CampiPortataVuotiException extends PersonalizzaMenuException {

    private static String messaggioDefault = "Almeno uno dei Campi è vuoto";

    public CampiPortataVuotiException(String s) {
        super(s);
    }

    public CampiPortataVuotiException() {
        super(messaggioDefault);
    }
}
