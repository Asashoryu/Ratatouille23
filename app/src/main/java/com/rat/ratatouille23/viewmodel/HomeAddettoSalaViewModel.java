package com.rat.ratatouille23.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeAddettoSalaViewModel extends ViewModel {

    public MutableLiveData<Boolean> vaiARegistraOrdinazione = new MutableLiveData<>(false);

    public void setVaiARegistraOrdinazione() {
        vaiARegistraOrdinazione.setValue(true);
        vaiARegistraOrdinazione.setValue(false);
    }
}
