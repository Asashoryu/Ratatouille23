package com.rat.ratatouille23.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rat.ratatouille23.repository.Repository;

import java.io.IOException;

public class HomeAddettoSalaViewModel extends ViewModel {

    Repository repository;

    public MutableLiveData<Boolean> vaiARegistraOrdinazione = new MutableLiveData<>(false);

    public MutableLiveData<String> messaggioHomeAddettoSala = new MutableLiveData<>("");

    public HomeAddettoSalaViewModel() {
        repository = Repository.getInstance();
        repository.setHomeAddettoSalaViewModel(this);
    }

    public void loadPerRegistrareOrdinazione() {
        try {
            repository.loadMenu();
            repository.loadTavoli();
            repository.loadOrdinazioniAndStoricoOrdinazioni();
            repository.loadPortateOrdine();
        } catch (IOException e) {
            setMessaggioHomeAddettoSala(e.getMessage());
        }
    }

    public void setVaiARegistraOrdinazione() {
        vaiARegistraOrdinazione.setValue(true);
        vaiARegistraOrdinazione.setValue(false);
    }

    public void setMessaggioHomeAddettoSala(String nuovoMessaggioHomeAddettoSala) {
        messaggioHomeAddettoSala.setValue(nuovoMessaggioHomeAddettoSala);
    }

    public String getMessaggioHomeAddettoSala() {
        return messaggioHomeAddettoSala.getValue();
    }


    public Boolean isNuovoMessaggioHomeAddettoSala() {
        return getMessaggioHomeAddettoSala() != "";
    }

    public void cancellaMessaggioHomeAddettoSala() {
        messaggioHomeAddettoSala.setValue("");
    }

}
