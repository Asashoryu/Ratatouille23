package com.rat.ratatouille23.eccezioni;

public class OrdinazioneException extends Ratatouille23Exception {

    private static String messaggioDefault = "Errore nell'ordinazione";
    public OrdinazioneException(String s) {
        super(s);
    }

    public OrdinazioneException() {
        super();
    }
}
