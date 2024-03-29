package com.rat.ratatouille23.eccezioni.rat.login;

import com.rat.ratatouille23.eccezioni.rat.login.LoginException;

public class DipendenteNonTrovatoException extends LoginException {

    private static String messaggioDefault = "Non esiste un dipendente con le credenziali indicate";

    public DipendenteNonTrovatoException(String s) {
        super(s);
    }

    public DipendenteNonTrovatoException() {
        super(messaggioDefault);
    }
}
