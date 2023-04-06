package com.rat.ratatouille23.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rat.ratatouille23.repository.Repository;

import java.io.IOException;

public class HomeSupervisoreViewModel extends ViewModel {

    Repository repository;
    public MutableLiveData<Boolean> vaiAlMenu = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> vaiAllaDispensa = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> vaiAlConto = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> vaiAdAssociaIngredienti = new MutableLiveData<>(false);

    public MutableLiveData<String> messaggioHomeSupervisore = new MutableLiveData<>("");

    public MutableLiveData<Boolean> logOut = new MutableLiveData<>(false);

    public HomeSupervisoreViewModel() {
        repository = Repository.getInstance();
        repository.setHomeSupervisoreViewModel(this);
    }

    public void setVaiAlMenu() {
        vaiAlMenu.setValue(true);
        vaiAlMenu.setValue(false);
    }

    public void setVaiAllaDispensa() {
        vaiAllaDispensa.setValue(true);
        vaiAllaDispensa.setValue(false);
    }

    public void setVaiAlConto() {
        vaiAlConto.setValue(true);
        vaiAlConto.setValue(false);
    }

    public void setVaiAdAssociaIngredienti() {
        vaiAdAssociaIngredienti.setValue(true);
        vaiAdAssociaIngredienti.setValue(false);
    }

    public void setLogOut() {
        logOut.setValue(true);
        logOut.setValue(false);
    }

    public void setMessaggioHomeSupervisore(String nuovoMessaggioHomeSupervisore) {
        messaggioHomeSupervisore.setValue(nuovoMessaggioHomeSupervisore);
    }

    public String getMessaggioHomeSupervisore() {
        return messaggioHomeSupervisore.getValue();
    }


    public Boolean isNuovoMessaggioHomeSupervisore() {
        return getMessaggioHomeSupervisore() != "";
    }

    public void cancellaMessaggioHomeSupervisore() {
        messaggioHomeSupervisore.setValue("");
    }

    public void loadPerVisualizzareConto() {

        try {
            repository.loadMenu();
            repository.loadIngredienti();
            repository.loadAssociazioniPiattiIngredienti();
        } catch (IOException e) {
            setMessaggioHomeSupervisore(e.getMessage());
        }

    }

    public void loadPerAssociaIngredienti() {
        try {
            repository.loadMenu();
            repository.loadIngredienti();
            repository.loadAssociazioniPiattiIngredienti();
        } catch (IOException e) {
            setMessaggioHomeSupervisore(e.getMessage());
        }
    }

    public void loadPerPersonalizzaMenu() {
        try {
            repository.loadMenu();
        } catch (IOException e) {
            setMessaggioHomeSupervisore(e.getMessage());
        }
    }

    public void loadPerGestioneTavolo() {
        try {
            repository.loadTavoli();
        } catch (IOException e) {
            setMessaggioHomeSupervisore(e.getMessage());
        }
    }
}
