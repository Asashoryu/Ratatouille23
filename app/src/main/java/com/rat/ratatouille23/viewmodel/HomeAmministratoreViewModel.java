package com.rat.ratatouille23.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rat.ratatouille23.repository.LoadRepository;

import java.io.IOException;

public class HomeAmministratoreViewModel extends ViewModel {

    LoadRepository loadRepository;
    public MutableLiveData<Boolean> vaiAlMenu = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> vaiAlleStatistiche = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> vaiAdAggiungiDipendente = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> vaiAllaGestioneTavolo = new MutableLiveData<>(false);

    public MutableLiveData<String> messaggioHomeAmministratore = new MutableLiveData<>("");

    public MutableLiveData<Boolean> logOut = new MutableLiveData<>(false);

    public HomeAmministratoreViewModel() {
        loadRepository = new LoadRepository();
    }

    public void setVaiAlMenu() {
        vaiAlMenu.setValue(true);
        vaiAlMenu.setValue(false);
    }

    public void setVaiAlleStatistiche() {
        vaiAlleStatistiche.setValue(true);
        vaiAlleStatistiche.setValue(false);
    }

    public void setVaiAdAggiungiDipendente() {
        vaiAdAggiungiDipendente.setValue(true);
        vaiAdAggiungiDipendente.setValue(false);
    }

    public void setVaiAllaGestioneTavolo() {
        vaiAllaGestioneTavolo.setValue(true);
        vaiAllaGestioneTavolo.setValue(false);
    }

    public void setMessaggioHomeAmministratore(String nuovoMessaggioHomeAmministratore) {
        messaggioHomeAmministratore.setValue(nuovoMessaggioHomeAmministratore);
    }

    public String getMessaggioHomeAmministratore() {
        return messaggioHomeAmministratore.getValue();
    }


    public Boolean isNuovoMessaggioHomeAmministratore() {
        return getMessaggioHomeAmministratore() != "";
    }

    public void cancellaMessaggioHomeAmministratore() {
        messaggioHomeAmministratore.setValue("");
    }

    public void loadPerStatistiche() throws IOException {
        loadRepository.loadMenuBackend();
        loadRepository.loadTavoliBackend();
        loadRepository.loadOrdinazioniEStoricoOrdinazioniBackend();
        loadRepository.loadPiattiOrdinatiBackend();
    }

    public void loadPerAggiuntadipendente() throws IOException {
    }

    public void loadPerGestioneTavolo() throws IOException {
        loadRepository.loadTavoliBackend();

    }

    public void loadPerPersonalizzaMenu() throws IOException {
        loadRepository.loadMenuBackend();

    }

    public void setLogOut() {
        logOut.setValue(true);
        logOut.setValue(false);
    }
}
