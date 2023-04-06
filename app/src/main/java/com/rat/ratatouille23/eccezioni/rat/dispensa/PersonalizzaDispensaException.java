package com.rat.ratatouille23.eccezioni.rat.dispensa;

import com.rat.ratatouille23.eccezioni.rat.Ratatouille23Exception;

public class PersonalizzaDispensaException extends Ratatouille23Exception {

    private static String messaggioDefault = "Uno dei seguenti campi è vuoto";

    public PersonalizzaDispensaException (String s) {
        super(s);
    }

    public PersonalizzaDispensaException() {
        super(messaggioDefault);
    }
}
