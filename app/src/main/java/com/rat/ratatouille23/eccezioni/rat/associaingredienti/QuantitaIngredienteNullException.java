package com.rat.ratatouille23.eccezioni.rat.associaingredienti;

public class QuantitaIngredienteNullException extends QuantiaIngredienteException {

    private static String messaggioDefault = "Quantita non inserita";

    public QuantitaIngredienteNullException(String s) {
        super(s);
    }

    public QuantitaIngredienteNullException() {
        super(messaggioDefault);
    }
}
