package com.rat.ratatouille23.eccezioni;

public class ReimpostaPasswordException extends Ratatouille23Exception {

    private static String messaggioDefault = "Errore nel reimpostare la password";

    public ReimpostaPasswordException(String s) {
        super(s);
    }
    public ReimpostaPasswordException() {
        super(messaggioDefault);
    }
}
