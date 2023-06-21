package com.rat.ratatouille23.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rat.ratatouille23.eccezioni.rat.tavoli.IndiceNegativoException;
import com.rat.ratatouille23.model.Tavolo;
import com.rat.ratatouille23.repository.Repository;
import com.rat.ratatouille23.repository.TavoliRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ModificaTavoliViewModel extends ViewModel {

    Repository repository;

    TavoliRepository tavoliRepository;
    public MutableLiveData<ArrayList<Tavolo>> listaTavoli = new MutableLiveData<ArrayList<Tavolo>>();

    public MutableLiveData<Boolean> vaiAdAggiungiTavolo = new MutableLiveData<>(false);

    public MutableLiveData<String> messaggioModificaTavoli = new MutableLiveData<>("");

    public MutableLiveData<Boolean> tornaIndietro = new MutableLiveData<>(false);

    public ModificaTavoliViewModel() {
        repository = Repository.getInstance();
        Repository.modificaTavoliViewModel = this;

        tavoliRepository = new TavoliRepository();

        setListaTavoli();
    }

    public void setListaTavoli() {
        aggiornaListaTavoli();
        repository.getTavoli().forEach(ingrediente -> System.out.println(ingrediente.getId()));
    }

    public void aggiornaListaTavoli() {
        listaTavoli.setValue(repository.getTavoli());
    }

    public void rimuoviTavolo(Tavolo tavolo) {
        try {
            tavoliRepository.deleteTableBackend(tavolo.getId());

            repository.getTavoli().remove(tavolo);
            aggiornaListaTavoli();
        } catch (IOException e) {
            setMessaggioModificaTavoli(e.getMessage());
        }
    }

    public void aggiungiTavolo() {
        try {
            aggiungiTavoloInOrdine();
        } catch (IOException e) {
            setMessaggioModificaTavoli(e.getMessage());
        }
    }

    public void aggiungiTavoloInOrdine() throws IOException {

        int nextAvailableIndex = getMinimoIndiceTavolo(repository.getTavoli());
        tavoliRepository.addTableBackend(nextAvailableIndex);

        // Create the new tavolo object with the determined name
        Tavolo newTavolo = new Tavolo(nextAvailableIndex, true);

        // Add the new tavolo object to the ArrayList
        repository.getTavoli().add(newTavolo);

        // Sort the ArrayList based on the tavolo names
        Collections.sort(repository.getTavoli(), new Comparator<Tavolo>() {
            @Override
            public int compare(Tavolo tavolo1, Tavolo tavolo2) {
                try {
                    return ModificaTavoliViewModel.this.compare(tavolo1.getId(), tavolo2.getId());
                } catch (IndiceNegativoException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        // aggiorna view
        aggiornaListaTavoli();
    }

    public int compare(int indiceTavolo1, int indiceTavolo2) throws IndiceNegativoException {
        if (indiceTavolo1 < 0 || indiceTavolo2 < 0) {
            throw new IndiceNegativoException();
        }

        int tavolo1Index = 0;
        int tavolo2Index = 0;

        tavolo1Index = indiceTavolo1;
        tavolo2Index = indiceTavolo2;

        return tavolo1Index - tavolo2Index;
    }

    public int getMinimoIndiceTavolo(ArrayList<Tavolo> tavoli) {
        // Determine the minimum available index for the new tavolo's name
        int nextAvailableIndex = 1;
        for (Tavolo tavolo : tavoli) {
            int tavoloIndex = 0;
            try {
                tavoloIndex = tavolo.getId();
            } catch (NumberFormatException e) {
                // Ignore tavoli with non-integer names
            }
            if (tavoloIndex == nextAvailableIndex) {
                nextAvailableIndex++;
            } else if (tavoloIndex > nextAvailableIndex) {
                break;
            }
        }
        return nextAvailableIndex;
    }

    public void impostaTavoloSelezionato(Tavolo tavolo) {
        repository.setTavoloSelezionato(tavolo);
    }

    public void setMessaggioModificaTavoli(String nuovoMessaggioModificaTavoli) {
        messaggioModificaTavoli.setValue(nuovoMessaggioModificaTavoli);
    }

    public String getMessaggioModificaTavoli() {
        return messaggioModificaTavoli.getValue();
    }


    public Boolean isNuovoMessaggioModificaTavoli() {
        return getMessaggioModificaTavoli() != "";
    }

    public void cancellaMessaggioModificaTavoli() {
        messaggioModificaTavoli.setValue("");
    }

    public void setTornaIndietro() {
        tornaIndietro.setValue(true);
        tornaIndietro.setValue(false);
    }

}
