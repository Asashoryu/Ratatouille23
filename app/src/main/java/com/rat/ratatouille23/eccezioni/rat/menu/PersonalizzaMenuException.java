package com.rat.ratatouille23.eccezioni.rat.menu;

import com.rat.ratatouille23.eccezioni.rat.Ratatouille23Exception;

public class PersonalizzaMenuException extends Ratatouille23Exception {

    private static String messaggioDefault = "Errore nella personalizzazione del menu";

    public PersonalizzaMenuException(String s) {
        super(s);
    }

    public PersonalizzaMenuException() {
        super(messaggioDefault);
    }
}
