package com.rat.ratatouille23.eccezioni.rat.login;

public class PasswordNonForteException extends ReimpostaPasswordException {
    private static String messaggioDefault = "Non esiste un dipendente con le credenziali indicate";

    public PasswordNonForteException(String s) {
        super(s);
    }

    public PasswordNonForteException() {
        super(messaggioDefault);
    }
}
