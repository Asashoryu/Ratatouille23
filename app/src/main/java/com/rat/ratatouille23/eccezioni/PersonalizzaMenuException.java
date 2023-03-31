package com.rat.ratatouille23.eccezioni;

public class PersonalizzaMenuException extends Ratatouille23Exception {

    private static String messaggioDefault = "Errore nella personalizzazione del menu";

    public PersonalizzaMenuException(String s) {
        super(s);
    }

    public PersonalizzaMenuException() {
        super(messaggioDefault);
    }
}
