package com.rat.ratatouille23.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeAmministratoreViewModel extends ViewModel {

    public MutableLiveData<Boolean> vaiAlMenu = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> vaiAlleStatistiche = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> vaiAdAggiungiDipendente = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> vaiAllaGestioneTavolo = new MutableLiveData<>(false);

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
}
