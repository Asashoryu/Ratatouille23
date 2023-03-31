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

    public MutableLiveData<String> messaggioDispensa = new MutableLiveData<>("");

    public DispensaViewModel() {
        repository = Repository.getInstance();
        repository.setDispensaViewModel(this);

        setListaIngredienti();
    }

    public void setListaIngredienti() {
        aggiornaListaIngredienti();
        repository.getIngredienti().forEach(ingrediente -> System.out.println(ingrediente.getNome()));
    }

    public void aggiornaListaIngredienti() {
        listaIngredienti.setValue(repository.getIngredienti());
    }

    public void setVaiAdAggiungiIngrediente() {
        vaiAdAggiungiIngrediente.setValue(true);
    }
    public void setFalseVaiAdAggiungiIngrediente() {
        vaiAdAggiungiIngrediente.setValue(false);
    }

    public void impostaIngredienteSelezionato(Ingrediente ingrediente) {
        repository.setIngredienteSelezionato(ingrediente);
    }

    public void setMessaggioDispensa(String nuovoMessaggioDispensa) {
        messaggioDispensa.setValue(nuovoMessaggioDispensa);
    }

    public String getMessaggioDispensa() {
        return messaggioDispensa.getValue();
    }


    public Boolean isNuovoMessaggioDispensa() {
        return getMessaggioDispensa() != "";
    }

    public void cancellaMessaggioDispensa() {
        messaggioDispensa.setValue("");
    }
}
