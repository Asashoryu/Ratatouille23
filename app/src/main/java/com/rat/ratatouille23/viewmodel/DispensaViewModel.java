package com.rat.ratatouille23.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rat.ratatouille23.model.Ingrediente;
import com.rat.ratatouille23.repository.Repository;

import java.util.ArrayList;
import java.util.Arrays;

public class DispensaViewModel extends ViewModel {

    Repository repository;
    public MutableLiveData<ArrayList<Ingrediente>> listaIngredienti = new MutableLiveData<ArrayList<Ingrediente>>();

    public MutableLiveData<Boolean> vaiAdAggiungiIngrediente = new MutableLiveData<>(false);

    public DispensaViewModel() {
        repository = Repository.getInstance();
        repository.setDispensaViewModel(this);

        setListaIngredienti();
    }

    public void setListaIngredienti() {
        listaIngredienti.setValue(repository.getIngredienti());
        repository.getIngredienti().forEach(ingrediente -> System.out.println(ingrediente.getNome()));
    }

    public void setVaiAdAggiungiIngrediente() {
        vaiAdAggiungiIngrediente.setValue(true);
    }
    public void setFalseVaiAdAggiungiIngrediente() {
        vaiAdAggiungiIngrediente.setValue(false);
    }
}
