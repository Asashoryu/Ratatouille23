package com.rat.ratatouille23.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rat.ratatouille23.model.Tavolo;
import com.rat.ratatouille23.repository.Repository;

import java.io.IOException;
import java.util.ArrayList;

public class ScegliTavoloOrdinazioneViewModel extends ViewModel {

    Repository repository;
    public MutableLiveData<ArrayList<Tavolo>> listaTavoli = new MutableLiveData<ArrayList<Tavolo>>();

    public MutableLiveData<Boolean> vaiAdAggiungiTavolo = new MutableLiveData<>(false);

    public MutableLiveData<String> messaggioScegliTavoloOrdinazione = new MutableLiveData<>("");

    public MutableLiveData<Boolean> tornaIndietro = new MutableLiveData<>(false);

    public ScegliTavoloOrdinazioneViewModel() {
        repository = Repository.getInstance();
        repository.setScegliTavoloOrdinazioneViewModel(this);

        setListaTavoli();
    }

    public void setListaTavoli() {
        aggiornaListaTavoli();
        repository.getTavoli().forEach(tavolo -> System.out.println(tavolo.getId()));
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

    public void setMessaggioScegliTavoloOrdinazione(String nuovoMessaggioScegliTavoloOrdinazione) {
        messaggioScegliTavoloOrdinazione.setValue(nuovoMessaggioScegliTavoloOrdinazione);
    }

    public String getMessaggioScegliTavoloOrdinazione() {
        return messaggioScegliTavoloOrdinazione.getValue();
    }


    public Boolean isNuovoMessaggioScegliTavoloOrdinazione() {
        return getMessaggioScegliTavoloOrdinazione() != "";
    }

    public void cancellaMessaggioScegliTavoloOrdinazione() {
        messaggioScegliTavoloOrdinazione.setValue("");
    }

    public void setTornaIndietro() {
        tornaIndietro.setValue(true);
        tornaIndietro.setValue(true);
    }
}
