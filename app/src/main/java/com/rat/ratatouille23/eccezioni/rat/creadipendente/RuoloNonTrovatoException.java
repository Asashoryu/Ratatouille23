package com.rat.ratatouille23.eccezioni.rat.creadipendente;

import com.rat.ratatouille23.eccezioni.rat.creadipendente.AggiungiDipendenteException;

public class RuoloNonTrovatoException extends AggiungiDipendenteException {

    private static String messaggioDefault = "Il ruolo indicato non è stato trovato";

    public RuoloNonTrovatoException(String s) {
        super(s);
    }

    public RuoloNonTrovatoException() {
        super(messaggioDefault);
    }
}
