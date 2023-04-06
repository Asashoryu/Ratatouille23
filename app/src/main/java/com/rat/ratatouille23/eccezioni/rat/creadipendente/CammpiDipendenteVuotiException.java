package com.rat.ratatouille23.eccezioni.rat.creadipendente;

public class CammpiDipendenteVuotiException extends  AggiungiDipendenteException{

    private static String messaggioDefault = "Almeno uno dei Campi è vuoto";

    public CammpiDipendenteVuotiException(String s) {
        super(s);
    }

    public CammpiDipendenteVuotiException() {
        super(messaggioDefault);
    }
}
