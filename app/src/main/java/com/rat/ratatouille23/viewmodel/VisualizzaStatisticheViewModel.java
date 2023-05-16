package com.rat.ratatouille23.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.github.mikephil.charting.data.Entry;
import com.rat.ratatouille23.model.Ordinazione;
import com.rat.ratatouille23.model.StoricoOrdinazioniChiuse;
import com.rat.ratatouille23.repository.Repository;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

public class VisualizzaStatisticheViewModel extends ViewModel {
    Repository repository;

    private StoricoOrdinazioniChiuse storicoOrdinazioniChiuse;


    public MutableLiveData<Boolean> tornaIndietro = new MutableLiveData<>(false);

    public MutableLiveData<String> messaggioVisualizzaStatistiche = new MutableLiveData<>("");

    public MutableLiveData<List<Entry>> entriesGrafico = new MutableLiveData<>();

    public VisualizzaStatisticheViewModel() {
        repository = Repository.getInstance();
        repository.setVisualizzaStatisticheViewModel(this);

        storicoOrdinazioniChiuse = repository.getStoricoOrdinazioniChiuse();
    }

    public LiveData<List<Entry>> getEntriesGrafico() {
        return entriesGrafico;
    }

    public void loadEntries(int anno) {
        List<Entry> listaVociDati = new ArrayList<>();
        String tempoOrdinazione;
        storicoOrdinazioniChiuse.getOrdinazioni().forEach(ordinazione -> System.out.println(ordinazione.getCostoTotalePortateOrdine()));

        // Recupera dati
        Map<Integer, Float> totaliGiornalieri = new HashMap<>();
        for (Ordinazione ordinazione : storicoOrdinazioniChiuse.getOrdinazioni()) {
            tempoOrdinazione = ordinazione.getMinutaggioChiusuraConto();
            int annoOrdine = getAnnoDaUTC(tempoOrdinazione);
            if (annoOrdine == anno) {
                int giornoAnno = getGiornoAnnoOffset(tempoOrdinazione);
                float costoPortate = ordinazione.getCostoTotalePortateOrdine();
                if (totaliGiornalieri.containsKey(giornoAnno)) {
                    costoPortate += totaliGiornalieri.get(giornoAnno);
                }
                totaliGiornalieri.put(giornoAnno, costoPortate);
            }
        }

        // Converti i totali giornalieri in una lista di voci dati
        for (Map.Entry<Integer, Float> voce : totaliGiornalieri.entrySet()) {
            listaVociDati.add(new Entry(voce.getKey(), voce.getValue()));
            System.err.println("Y: " + voce.getValue() + ", X: " + voce.getKey());
        }

        entriesGrafico.setValue(listaVociDati);
    }

    private int getAnnoDaUTC(String valoreUTC) {
        long timestamp = Long.parseLong(valoreUTC);
        Date data = new Date(timestamp * 1000);
        Calendar calendario = Calendar.getInstance();
        calendario.setTimeZone(TimeZone.getTimeZone("UTC"));
        calendario.setTime(data);
        int anno = calendario.get(Calendar.YEAR);
        System.err.println("anno: " + anno);
        return anno;
    }

    public int getGiornoAnnoOffset(String valoreUTC) {
        long timestamp = Long.parseLong(valoreUTC);
        Instant istante = Instant.ofEpochSecond(timestamp);
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(istante, ZoneOffset.UTC);
        int giornoAnno = zonedDateTime.getDayOfYear();
        System.err.println("giornoAnno: " + giornoAnno);
        return giornoAnno;
    }

    public boolean verificaSeEntryEsiste(float coordinataX) {
        if (entriesGrafico == null) {
            return false;
        }
        for (Entry voce : entriesGrafico.getValue()) {
            if (voce.getX() == coordinataX) {
                return true;
            }
        }
        return false;
    }

    public float getCoordinataYDaCoordinataX(float coordinataX) {
        for (Entry voce : entriesGrafico.getValue()) {
            if (voce.getX() == coordinataX) {
                return voce.getY();
            }
        }
        return -1; // Se la voce con la coordinata x fornita non viene trovata, restituisci -1 o un altro valore appropriato
    }

    public void setTornaIndietro() {
        tornaIndietro.setValue(true);
    }

    public void setFalseTornaIndietro() {
        tornaIndietro.setValue(false);
    }

    public void setMessaggioVisualizzaStatistiche(String nuovoMessaggioVisualizzaStatistiche) {
        messaggioVisualizzaStatistiche.setValue(nuovoMessaggioVisualizzaStatistiche);
    }

    public String getMessaggioVisualizzaStatistiche() {
        return messaggioVisualizzaStatistiche.getValue();
    }

    public Boolean isNuovoMessaggioVisualizzaStatistiche() {
        return getMessaggioVisualizzaStatistiche() != "";
    }

    public void cancellaMessaggioVisualizzaStatistiche() {
        messaggioVisualizzaStatistiche.setValue("");
    }
}
