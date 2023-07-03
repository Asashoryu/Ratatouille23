package com.rat.ratatouille23.eccezioni.rat.creadipendente;

import com.rat.ratatouille23.eccezioni.rat.Ratatouille23Exception;

public class CreazioneDipendenteException extends Ratatouille23Exception {

    private static String messaggioDefault = "Errore nella creazione del dipendente";

    public CreazioneDipendenteException(String s) {
        super(s);
    }

    public CreazioneDipendenteException() {
        super();
    }
}
