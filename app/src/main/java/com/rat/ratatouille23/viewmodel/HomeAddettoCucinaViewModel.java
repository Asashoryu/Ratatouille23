package com.rat.ratatouille23.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rat.ratatouille23.utility.NomeTipo;

public class HomeAddettoCucinaViewModel extends ViewModel {

    public MutableLiveData<String> vaiAllaDispensa = new MutableLiveData<>(NomeTipo.FALSE);
    public MutableLiveData<String> vaiAdAssociaIngredienti = new MutableLiveData<>(NomeTipo.FALSE);

    public void setVaiAllaDispensa() {
        vaiAllaDispensa.setValue(NomeTipo.TRUE);
        vaiAllaDispensa.setValue(NomeTipo.FALSE);
    }

    public void setVaiAdAssociaIngredienti() {
        vaiAdAssociaIngredienti.setValue(NomeTipo.TRUE);
        vaiAdAssociaIngredienti.setValue(NomeTipo.FALSE);
    }
}
