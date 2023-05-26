package com.rat.ratatouille23.eccezioni.rat.visualizzastatistiche;

import com.rat.ratatouille23.eccezioni.rat.Ratatouille23Exception;

public class VisualizzaStatisticheException extends Ratatouille23Exception {

    private static String messaggioDefault = "Errore nella visualizzazione delle statistiche";

    public VisualizzaStatisticheException(String s) {
        super(s);
    }

    public VisualizzaStatisticheException() {
        super();
    }
}
