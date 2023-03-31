package com.rat.ratatouille23.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rat.ratatouille23.model.Tavolo;
import com.rat.ratatouille23.repository.Repository;

import java.io.IOException;
import java.util.ArrayList;

public class ModificaTavoliViewModel extends ViewModel {

    Repository repository;
    public MutableLiveData<ArrayList<Tavolo>> listaTavoli = new MutableLiveData<ArrayList<Tavolo>>();

    public MutableLiveData<Boolean> vaiAdAggiungiTavolo = new MutableLiveData<>(false);

    public MutableLiveData<String> messaggioModificaTavoli = new MutableLiveData<>("");

    public ModificaTavoliViewModel() {
        repository = Repository.getInstance();
        repository.setModificaTavoliViewModel(this);

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
            repository.rimuoviTavolo(tavolo);
        } catch (IOException e) {
            setMessaggioModificaTavoli(e.getMessage());
        }
    }

    public void aggiungiTavolo() {
        try {
            repository.aggiungiTavoloInOrdine();
        } catch (IOException e) {
            setMessaggioModificaTavoli(e.getMessage());
        }
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

}
