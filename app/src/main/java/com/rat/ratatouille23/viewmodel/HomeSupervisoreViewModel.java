package com.rat.ratatouille23.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rat.ratatouille23.utility.NomeTipo;

public class HomeSupervisoreViewModel extends ViewModel {
    public MutableLiveData<String> vaiAlMenu = new MutableLiveData<>(NomeTipo.FALSE);
    public MutableLiveData<String> vaiAllaDispensa = new MutableLiveData<>(NomeTipo.FALSE);
    public MutableLiveData<String> vaiAlConto = new MutableLiveData<>(NomeTipo.FALSE);
    public MutableLiveData<String> vaiAdAssociaIngredienti = new MutableLiveData<>(NomeTipo.FALSE);

    public void setVaiAlMenu() {
        vaiAlMenu.setValue(NomeTipo.TRUE);
        vaiAlMenu.setValue(NomeTipo.FALSE);
    }

    public void setVaiAllaDispensa() {
        vaiAllaDispensa.setValue(NomeTipo.TRUE);
        vaiAllaDispensa.setValue(NomeTipo.FALSE);
    }

    public void setVaiAlConto() {
        vaiAlConto.setValue(NomeTipo.TRUE);
        vaiAlConto.setValue(NomeTipo.FALSE);
    }

    public void setVaiAdAssociaIngredienti() {
        vaiAdAssociaIngredienti.setValue(NomeTipo.TRUE);
        vaiAdAssociaIngredienti.setValue(NomeTipo.FALSE);
    }
}
