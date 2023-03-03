package com.rat.ratatouille23.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rat.ratatouille23.utility.NomeTipo;

public class HomeAmministratoreViewModel extends ViewModel {

    public MutableLiveData<String> vaiAlMenu = new MutableLiveData<>(NomeTipo.FALSE);
    public MutableLiveData<String> vaiAlleStatistiche = new MutableLiveData<>(NomeTipo.FALSE);
    public MutableLiveData<String> vaiAdAggiungiDipendente = new MutableLiveData<>(NomeTipo.FALSE);
    public MutableLiveData<String> vaiAllaGestioneTavolo = new MutableLiveData<>(NomeTipo.FALSE);

    public void setVaiAlMenu() {
        vaiAlMenu.setValue(NomeTipo.TRUE);
        vaiAlMenu.setValue(NomeTipo.FALSE);
    }

    public void setVaiAlleStatistiche() {
        vaiAlleStatistiche.setValue(NomeTipo.TRUE);
        vaiAlleStatistiche.setValue(NomeTipo.FALSE);
    }

    public void setVaiAdAggiungiDipendente() {
        vaiAdAggiungiDipendente.setValue(NomeTipo.TRUE);
        vaiAdAggiungiDipendente.setValue(NomeTipo.FALSE);
    }

    public void setVaiAllaGestioneTavolo() {
        vaiAllaGestioneTavolo.setValue(NomeTipo.TRUE);
        vaiAllaGestioneTavolo.setValue(NomeTipo.FALSE);
    }
}
