package com.rat.ratatouille23.eccezioni.rat.tavoli;

import com.rat.ratatouille23.eccezioni.rat.Ratatouille23Exception;

public class IndiceNegativoException extends Ratatouille23Exception {

    private static String messaggioDefault = "L'indice del tavolo non può essere negativo";
    public IndiceNegativoException(String s) {
        super(s);
    }

    public IndiceNegativoException() {
        super();
    }
}
