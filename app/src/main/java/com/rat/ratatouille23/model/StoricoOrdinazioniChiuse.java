package com.rat.ratatouille23.model;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

public class StoricoOrdinazioniChiuse {
    private ArrayList<Ordinazione> ordinazioni;

    private static StoricoOrdinazioniChiuse questoStoricoOrdinazioniChiuse;

    private StoricoOrdinazioniChiuse() {
        ordinazioni = new ArrayList<>();
    }

    public static StoricoOrdinazioniChiuse getInstance() {
        if (questoStoricoOrdinazioniChiuse == null) {
            questoStoricoOrdinazioniChiuse = new StoricoOrdinazioniChiuse();
        }
        return questoStoricoOrdinazioniChiuse;
    }

    public void chiudiOrdinazione(Ordinazione ordinazione) {
        ordinazione.setChiusa(true);
        ordinazione.setMinutaggioChiusuraConto(getMinutaggioAdesso());
        ordinazione.getTavolo().liberaTavoloDaOrdinazione();
        ordinazioni.add(ordinazione);
    }

    public String getMinutaggioAdesso() {
        return Long.toString(Instant.now().getEpochSecond());
    }

    public ArrayList<Ordinazione> getOrdinazioni() {
        return ordinazioni;
    }

    public void setOrdinazioni(ArrayList<Ordinazione> ordinazioni) {
        this.ordinazioni = ordinazioni;
    }

}
