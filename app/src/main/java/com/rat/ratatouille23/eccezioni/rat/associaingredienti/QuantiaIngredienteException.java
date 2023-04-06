package com.rat.ratatouille23.eccezioni.rat.associaingredienti;

import com.rat.ratatouille23.eccezioni.rat.Ratatouille23Exception;

public class QuantiaIngredienteException extends Ratatouille23Exception {

    private static String messaggioDefault = "Quantita non inserita";

    public QuantiaIngredienteException(String s) {
        super(s);
    }

    public QuantiaIngredienteException() {
        super(messaggioDefault);
    }
}
