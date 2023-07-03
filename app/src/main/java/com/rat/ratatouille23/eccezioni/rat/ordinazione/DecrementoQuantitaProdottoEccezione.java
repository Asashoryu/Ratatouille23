package com.rat.ratatouille23.eccezioni.rat.ordinazione;

import com.rat.ratatouille23.eccezioni.rat.ordinazione.OrdinazioneException;

public class DecrementoQuantitaProdottoEccezione extends OrdinazioneException {

    private static String messaggioDefault = "Impossibile decrementare la quantità di un prodotto sotto gli 0 elementi";

    public DecrementoQuantitaProdottoEccezione(String s) {
        super(s);
    }

    public DecrementoQuantitaProdottoEccezione() {
        super();
    }
}
