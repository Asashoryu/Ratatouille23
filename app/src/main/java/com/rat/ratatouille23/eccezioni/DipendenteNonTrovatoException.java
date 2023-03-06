package com.rat.ratatouille23.eccezioni;

public class DipendenteNonTrovatoException extends LoginException{

    private static String messaggioDefault = "Non esiste un dipendente con le credenziali indicate";

    public DipendenteNonTrovatoException(String s) {
        super(s);
    }
    public DipendenteNonTrovatoException() {
        super(messaggioDefault);
    }
}
