package com.rat.ratatouille23.eccezioni.rat.associaingredienti;

public class QuantitaIngredienteNegativaException extends QuantitaIngredienteException {

    private static String messaggioDefault = "Quantita inserita negativa";

    public QuantitaIngredienteNegativaException(String s) {
        super(s);
    }

    public QuantitaIngredienteNegativaException() {
        super(messaggioDefault);
    }
}
