package com.rat.ratatouille23.eccezioni.rat.associaingredienti;

import com.rat.ratatouille23.eccezioni.rat.Ratatouille23Exception;

public class QuantitaIngredienteException extends Ratatouille23Exception {

    private static String messaggioDefault = "Quantita non inserita";

    public QuantitaIngredienteException(String s) {
        super(s);
    }

    public QuantitaIngredienteException() {
        super(messaggioDefault);
    }
}
