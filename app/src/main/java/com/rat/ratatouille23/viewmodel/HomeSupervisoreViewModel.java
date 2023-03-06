package com.rat.ratatouille23.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeSupervisoreViewModel extends ViewModel {
    public MutableLiveData<Boolean> vaiAlMenu = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> vaiAllaDispensa = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> vaiAlConto = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> vaiAdAssociaIngredienti = new MutableLiveData<>(false);

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
}
