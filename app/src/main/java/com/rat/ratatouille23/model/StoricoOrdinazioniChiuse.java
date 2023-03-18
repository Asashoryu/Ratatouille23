package com.rat.ratatouille23.model;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

public class StoricoOrdinazioniChiuse {
    private ArrayList<Ordinazione> storico;

    private static StoricoOrdinazioniChiuse questoStoricoOrdinazioniChiuse;

    private StoricoOrdinazioniChiuse() {
        storico = new ArrayList<>();
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
        storico.add(ordinazione);
    }

    public void chiudiOrdinazioneInUTC(Ordinazione ordinazione, String UTC) {
        ordinazione.setChiusa(true);
        ordinazione.setMinutaggioChiusuraConto(UTC);
        storico.add(ordinazione);
    }

    private String getMinutaggioAdesso() {
        return Long.toString(Instant.now().toEpochMilli());
    }

    public ArrayList<Ordinazione> getOrdinazioni() {
        return storico;
    }

    public void setOrdinazioni(ArrayList<Ordinazione> ordinazioni) {
        this.storico = ordinazioni;
    }


}
