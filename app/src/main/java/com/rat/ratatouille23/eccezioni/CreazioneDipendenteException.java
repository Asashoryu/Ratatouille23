package com.rat.ratatouille23.eccezioni;

public class CreazioneDipendenteException extends Ratatouille23Exception {

    private static String messaggioDefault = "Errore nella creazione del dipendente";
    public CreazioneDipendenteException(String s) {
        super(s);
    }

    public CreazioneDipendenteException() {
        super();
    }
}
