package com.rat.ratatouille23.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rat.ratatouille23.model.Ingrediente;
import com.rat.ratatouille23.repository.Repository;

public class AggiungiIngredienteViewModel extends ViewModel {

    Repository repository;

    Ingrediente ingrediente;

    public MutableLiveData<Boolean> tornaIndietro = new MutableLiveData<>(false);

    public MutableLiveData<String> messaggioAggiungiIngrediente = new MutableLiveData<>("");

    public AggiungiIngredienteViewModel() {
        repository = Repository.getInstance();
        repository.setAggiungiIngredienteViewModel(this);
    }

    public void aggiungiIngrediente(String nome, String costo, String quantita, String unitaMisura, String descrizione) {
        ingrediente = null;
        ingrediente = new Ingrediente(nome, costo, quantita, unitaMisura, descrizione);
        System.err.println(nome + costo + quantita + unitaMisura + descrizione);

        repository.aggiungiIngrediente(ingrediente);
        repository.aggiornaListaIngredienti();

        setTornaIndietro();
    }

    public void setTornaIndietro() {
        tornaIndietro.setValue(true);
    }

    public void setFalseTornaIndietro() {
        tornaIndietro.setValue(false);
    }

    public void setMessaggioAggiungiIngrediente(String nuovoMessaggioAggiungiIngrediente) {
        messaggioAggiungiIngrediente.setValue(nuovoMessaggioAggiungiIngrediente);
    }

    public String getMessaggioAggiungiIngrediente() {
        return messaggioAggiungiIngrediente.getValue();
    }

    public Boolean isNuovoMessaggioAggiungiIngrediente() {
        return getMessaggioAggiungiIngrediente() != "";
    }

    public void cancellaMessaggioAggiungiIngrediente() {
        messaggioAggiungiIngrediente.setValue("");
    }
}
