package com.rat.ratatouille23.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rat.ratatouille23.model.Ingrediente;
import com.rat.ratatouille23.model.Portata;
import com.rat.ratatouille23.repository.Repository;

import java.util.ArrayList;

public class AssociaIngredientiViewModel extends ViewModel {
    Repository repository;

    Portata portata;
    public MutableLiveData<ArrayList<Ingrediente>> listaIngredientiPortata = new MutableLiveData<ArrayList<Ingrediente>>();

    public MutableLiveData<ArrayList<Ingrediente>> listaIngredientiNonPortata = new MutableLiveData<ArrayList<Ingrediente>>();

    public MutableLiveData<Boolean> vaiAdAggiungiIngrediente = new MutableLiveData<>(false);

    public MutableLiveData<Boolean> tornaIndietro = new MutableLiveData<>(false);

    public AssociaIngredientiViewModel() {
        repository = Repository.getInstance();
        repository.setAssociaIngredientiViewModel(this);

        portata = repository.getPortataSelezionata();

        setListaIngredientiPortata();
        setListaIngredientiNonPortata();
    }

    public void setListaIngredientiPortata() {
        aggiornaListaIngredientiPortata();
        repository.getIngredienti().forEach(ingrediente -> System.out.println(ingrediente.getNome()));
    }

    public void setListaIngredientiNonPortata() {
        aggiornaListaIngredientiNonPortata();
        repository.getIngredienti().forEach(ingrediente -> System.out.println(ingrediente.getNome()));
    }

    public void aggiornaListaIngredientiPortata() {
        listaIngredientiPortata.setValue(portata.getIngredienti());
    }

    public void aggiornaListaIngredientiNonPortata() {
        listaIngredientiNonPortata.setValue(repository.getIngredientiNonAssociatiAllaPortata(portata));
    }

    public void setVaiAdAggiungiIngrediente() {
        vaiAdAggiungiIngrediente.setValue(true);
    }
    public void setFalseVaiAdAggiungiIngrediente() {
        vaiAdAggiungiIngrediente.setValue(false);
    }

    public void aggiungiIngredienteAllaPortata(Ingrediente ingrediente, Float quantita) {
        Ingrediente ingredienteAssociato;
        ingredienteAssociato = new Ingrediente(ingrediente.getNome(), ingrediente.getCosto(), quantita, ingrediente.getUnitaMisura(), ingrediente.getUnitaMisura());
        portata.aggiungiIngrediente(ingredienteAssociato, quantita);
        aggiornaListaIngredientiPortata();
        aggiornaListaIngredientiNonPortata();
    }

    public void impostaIngredienteSelezionato(Ingrediente ingrediente) {
        repository.setIngredienteSelezionato(ingrediente);
    }

    public void setTornaIndietro() {
        tornaIndietro.setValue(true);
        tornaIndietro.setValue(false);
    }
}
