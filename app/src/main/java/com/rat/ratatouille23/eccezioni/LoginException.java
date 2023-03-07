package com.rat.ratatouille23.eccezioni;

public class LoginException extends Ratatouille23Exception {

    private static String messaggioDefault = "Errore nel reimpostare la password";
    public LoginException(String s) {
        super(s);
    }

    public LoginException() {
        super();
    }
}
