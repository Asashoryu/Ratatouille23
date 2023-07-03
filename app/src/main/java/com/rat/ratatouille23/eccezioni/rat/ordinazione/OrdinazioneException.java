package com.rat.ratatouille23.eccezioni.rat.ordinazione;

import com.rat.ratatouille23.eccezioni.rat.Ratatouille23Exception;

public class OrdinazioneException extends Ratatouille23Exception {

    private static String messaggioDefault = "Errore nell'ordinazione";

    public OrdinazioneException(String s) {
        super(s);
    }

    public OrdinazioneException() {
        super();
    }
}
