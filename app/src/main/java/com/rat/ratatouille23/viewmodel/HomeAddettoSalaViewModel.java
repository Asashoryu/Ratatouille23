package com.rat.ratatouille23.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rat.ratatouille23.utility.NomeTipo;

public class HomeAddettoSalaViewModel extends ViewModel {

    public MutableLiveData<String> vaiARegistraOrdinazione = new MutableLiveData<>(NomeTipo.FALSE);

    public void setVaiARegistraOrdinazione() {
        vaiARegistraOrdinazione.setValue(NomeTipo.TRUE);
        vaiARegistraOrdinazione.setValue(NomeTipo.FALSE);
    }
}
