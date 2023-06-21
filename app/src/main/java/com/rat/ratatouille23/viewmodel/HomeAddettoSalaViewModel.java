package com.rat.ratatouille23.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rat.ratatouille23.repository.LoadRepository;

import java.io.IOException;

public class HomeAddettoSalaViewModel extends ViewModel {

    LoadRepository loadRepository;

    public MutableLiveData<Boolean> vaiARegistraOrdinazione = new MutableLiveData<>(false);

    public MutableLiveData<String> messaggioHomeAddettoSala = new MutableLiveData<>("");

    public MutableLiveData<Boolean> logOut = new MutableLiveData<>(false);

    public HomeAddettoSalaViewModel() {
        loadRepository = new LoadRepository();
    }

    public void loadPerRegistrareOrdinazione() throws IOException{
        loadRepository.loadTavoliBackend();
        loadRepository.loadMenuBackend();
        loadRepository.loadOrdinazioniEStoricoOrdinazioniBackend();
        loadRepository.loadPiattiOrdinatiBackend();
        loadRepository.loadIngredientiBackend();
        loadRepository.loadAssociazioniPiattiIngredientiBackend();
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

    public void setLogOut() {
        logOut.setValue(true);
        logOut.setValue(false);
    }

}
