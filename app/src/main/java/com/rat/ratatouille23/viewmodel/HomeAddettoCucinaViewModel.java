package com.rat.ratatouille23.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rat.ratatouille23.repository.Repository;

import java.io.IOException;

public class HomeAddettoCucinaViewModel extends ViewModel {

    Repository repository;
    public MutableLiveData<Boolean> vaiAllaDispensa = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> vaiAdAssociaIngredienti = new MutableLiveData<>(false);
    public MutableLiveData<String> messaggioHomeAddettoCucina = new MutableLiveData<>("");
    public MutableLiveData<Boolean> logOut = new MutableLiveData<>(false);

    public HomeAddettoCucinaViewModel() {
        repository = Repository.getInstance();
        repository.setHomeAddettoCucinaViewModel(this);
    }

    public void setVaiAllaDispensa() {
        vaiAllaDispensa.setValue(true);
        vaiAllaDispensa.setValue(false);
    }

    public void setVaiAdAssociaIngredienti() {
        vaiAdAssociaIngredienti.setValue(true);
        vaiAdAssociaIngredienti.setValue(false);
    }

    public void loadPerDispensa() {
        try {
            repository.loadIngredienti();
        } catch (IOException e) {
            setMessaggioHomeAddettoCucina(e.getMessage());
        }
    }

    public void loadPerAssociaIngredienti() {
        try {
            repository.loadMenu();
            repository.loadIngredienti();
            repository.loadAssociazioniPiattiIngredienti();
        } catch (IOException e) {
            setMessaggioHomeAddettoCucina(e.getMessage());
        }
    }

    public void setMessaggioHomeAddettoCucina(String nuovoMessaggioHomeAddettoCucina) {
        messaggioHomeAddettoCucina.setValue(nuovoMessaggioHomeAddettoCucina);
    }

    public String getMessaggioHomeAddettoCucina() {
        return messaggioHomeAddettoCucina.getValue();
    }


    public Boolean isNuovoMessaggioHomeAddettoCucina() {
        return getMessaggioHomeAddettoCucina() != "";
    }

    public void cancellaMessaggioHomeAddettoCucina() {
        messaggioHomeAddettoCucina.setValue("");
    }

    public void setLogOut() {
        logOut.setValue(true);
        logOut.setValue(false);
    }
}
