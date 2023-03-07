package com.rat.ratatouille23.eccezioni;

public class RuoloNonTrovatoException extends AggiungiDipendenteException {

    private static String messaggioDefault = "Il ruolo indicato non è stato trovato";

    public RuoloNonTrovatoException(String s) {
        super(s);
    }

    public RuoloNonTrovatoException() {
        super(messaggioDefault);
    }
}
