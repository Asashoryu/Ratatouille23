package com.rat.ratatouille23.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.github.mikephil.charting.data.Entry;
import com.rat.ratatouille23.model.Ordinazione;
import com.rat.ratatouille23.model.StoricoOrdinazioniChiuse;
import com.rat.ratatouille23.repository.Repository;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    public MutableLiveData<List<Entry>> entries = new MutableLiveData<>();

    public VisualizzaStatisticheViewModel() {
        repository = Repository.getInstance();
        repository.setVisualizzaStatisticheViewModel(this);

        storicoOrdinazioniChiuse = repository.getStoricoOrdinazioniChiuse();
    }

    public LiveData<List<Entry>> getEntries() {
        return entries;
    }

    public void loadEntries(int year) {
        List<Entry> entryList = new ArrayList<>();
        String tempoOrdinazione;

        storicoOrdinazioniChiuse.getOrdinazioni().forEach(ordinazione -> System.out.println(ordinazione.getCostoTotalePortateOrdine()));

        // Replace the following loop with code that fetches real data from a database or API
        Map<Integer, Float> dailyTotals = new HashMap<>();
        for (Ordinazione ordinazione : storicoOrdinazioniChiuse.getOrdinazioni()) {
            tempoOrdinazione = ordinazione.getMinutaggioChiusuraConto();
            int yearOfOrder = getYearFromUTC(tempoOrdinazione);
            if (yearOfOrder == year) {
                int dayOfYear = getOffsetDayOfYear(tempoOrdinazione);
                float costoPortate = ordinazione.getCostoTotalePortateOrdine();
                if (dailyTotals.containsKey(dayOfYear)) {
                    costoPortate += dailyTotals.get(dayOfYear);
                }
                dailyTotals.put(dayOfYear, costoPortate);
            }
        }

        // Convert the daily totals to a list of entries
        for (Map.Entry<Integer, Float> entry : dailyTotals.entrySet()) {
            entryList.add(new Entry(entry.getKey(), entry.getValue()));
            System.err.println("Y: " + entry.getValue() + ", X: " + entry.getKey());
        }

        entries.setValue(entryList);
    }

    private int getYearFromUTC(String utcValue) {
        long timestamp = Long.parseLong(utcValue);
        Date date = new Date(timestamp * 1000);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        System.err.println("year: " + year);
        return year;
    }

    public int getOffsetDayOfYear(String utcValue) {
        long timestamp = Long.parseLong(utcValue);
        Instant instant = Instant.ofEpochSecond(timestamp);
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(instant, ZoneOffset.UTC);
        int dayOfYear = zonedDateTime.getDayOfYear();
        System.err.println("dayOfYear: " + dayOfYear);
        return dayOfYear;
    }

    public boolean checkEntryExists(float xCoordinate) {
        if (entries == null) {
            return false;
        }
        for (Entry entry : entries.getValue()) {
            if (entry.getX() == xCoordinate) {
                return true;
            }
        }
        return false;
    }

    public float getYCoordinateFromXCoordinate(float xCoordinate) {
        for (Entry entry : entries.getValue()) {
            if (entry.getX() == xCoordinate) {
                return entry.getY();
            }
        }
        return -1; // If entry with given x-coordinate is not found, return -1 or any other appropriate value
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
