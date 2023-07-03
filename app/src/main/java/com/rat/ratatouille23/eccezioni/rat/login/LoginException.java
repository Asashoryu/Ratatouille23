package com.rat.ratatouille23.eccezioni.rat.login;

import com.rat.ratatouille23.eccezioni.rat.Ratatouille23Exception;

public class LoginException extends Ratatouille23Exception {

    private static String messaggioDefault = "Errore nel reimpostare la password";

    public LoginException(String s) {
        super(s);
    }

    public LoginException() {
        super();
    }
}
