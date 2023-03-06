package com.rat.ratatouille23.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeAddettoCucinaViewModel extends ViewModel {

    public MutableLiveData<Boolean> vaiAllaDispensa = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> vaiAdAssociaIngredienti = new MutableLiveData<>(false);

    public void setVaiAllaDispensa() {
        vaiAllaDispensa.setValue(true);
        vaiAllaDispensa.setValue(false);
    }

    public void setVaiAdAssociaIngredienti() {
        vaiAdAssociaIngredienti.setValue(true);
        vaiAdAssociaIngredienti.setValue(false);
    }
}
