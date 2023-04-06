package com.rat.ratatouille23.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rat.ratatouille23.repository.Repository;

import java.io.IOException;

public class HomeAmministratoreViewModel extends ViewModel {

    Repository repository;
    public MutableLiveData<Boolean> vaiAlMenu = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> vaiAlleStatistiche = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> vaiAdAggiungiDipendente = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> vaiAllaGestioneTavolo = new MutableLiveData<>(false);

    public MutableLiveData<String> messaggioHomeAmministratore = new MutableLiveData<>("");

    public MutableLiveData<Boolean> logOut = new MutableLiveData<>(false);

    public HomeAmministratoreViewModel() {
        repository = Repository.getInstance();
        repository.setHomeAmministratoreViewModel(this);
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
        repository.loadMenu();
        repository.loadTavoli();
        repository.loadOrdinazioniAndStoricoOrdinazioni();
        repository.loadPortateOrdine();

    }

    public void loadPerAggiuntadipendente() throws IOException {
    }

    public void loadPerGestioneTavolo() throws IOException {
        repository.loadTavoli();

    }

    public void loadPerPersonalizzaMenu() throws IOException {
        repository.loadMenu();

    }

    public void setLogOut() {
        logOut.setValue(true);
        logOut.setValue(false);
    }
}
