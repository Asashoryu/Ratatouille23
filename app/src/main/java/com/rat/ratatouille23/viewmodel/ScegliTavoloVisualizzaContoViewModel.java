package com.rat.ratatouille23.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rat.ratatouille23.model.Tavolo;
import com.rat.ratatouille23.repository.Repository;

import java.io.IOException;
import java.util.ArrayList;

public class ScegliTavoloVisualizzaContoViewModel extends ViewModel {

    Repository repository;
    public MutableLiveData<ArrayList<Tavolo>> listaTavoli = new MutableLiveData<ArrayList<Tavolo>>();

    public MutableLiveData<Boolean> vaiAdAggiungiTavolo = new MutableLiveData<>(false);

    public MutableLiveData<String> messaggioScegliTavoloVisualizzaConto = new MutableLiveData<>("");

    public MutableLiveData<Boolean> tornaIndietro = new MutableLiveData<>(false);

    public ScegliTavoloVisualizzaContoViewModel() {
        repository = Repository.getInstance();
        repository.setScegliTavoloVisualizzaContoViewModel(this);

        setListaTavoli();
    }

    public void setListaTavoli() {
        aggiornaListaTavoli();
        repository.getTavoli().forEach(ingrediente -> System.out.println(ingrediente.getId()));
    }

    public void aggiornaListaTavoli() {
        listaTavoli.setValue(repository.getTavoli());
    }

    public void setVaiAdAggiungiTavolo() {
        vaiAdAggiungiTavolo.setValue(true);
    }
    public void setFalseVaiAdAggiungiTavolo() {
        vaiAdAggiungiTavolo.setValue(false);
    }

    public void impostaTavoloSelezionato(Tavolo tavolo) {
        repository.setTavoloSelezionato(tavolo);
    }

    public void setMessaggioScegliTavoloVisualizzaConto(String nuovoMessaggioScegliTavoloVisualizzaConto) {
        messaggioScegliTavoloVisualizzaConto.setValue(nuovoMessaggioScegliTavoloVisualizzaConto);
    }

    public String getMessaggioScegliTavoloVisualizzaConto() {
        return messaggioScegliTavoloVisualizzaConto.getValue();
    }


    public Boolean isNuovoMessaggioScegliTavoloVisualizzaConto() {
        return getMessaggioScegliTavoloVisualizzaConto() != "";
    }

    public void cancellaMessaggioScegliTavoloVisualizzaConto() {
        messaggioScegliTavoloVisualizzaConto.setValue("");
    }

    public void setTornaIndietro() {
        tornaIndietro.setValue(true);
        tornaIndietro.setValue(false);
    }
}
