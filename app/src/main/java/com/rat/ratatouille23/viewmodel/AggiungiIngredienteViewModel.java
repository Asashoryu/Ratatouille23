package com.rat.ratatouille23.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rat.ratatouille23.model.Ingrediente;
import com.rat.ratatouille23.repository.Repository;

import java.io.IOException;

public class AggiungiIngredienteViewModel extends ViewModel {

    Repository repository;

    Ingrediente ingrediente;

    public MutableLiveData<Boolean> tornaIndietro = new MutableLiveData<>(false);

    public MutableLiveData<String> messaggioAggiungiIngrediente = new MutableLiveData<>("");

    public AggiungiIngredienteViewModel() {
        repository = Repository.getInstance();
        repository.setAggiungiIngredienteViewModel(this);
    }

    public void aggiungiIngrediente(String nome, String costo, String quantita, String unitaMisura, String soglia, String descrizione) {
        ingrediente = null;

        if (isInputAggiungiIngredienteValido(nome, costo, quantita, unitaMisura, soglia, descrizione)) {
            Float valoreCosto = Float.parseFloat(costo);
            Float valoreQuantita = Float.parseFloat(quantita);
            Float valoreSoglia = Float.parseFloat(soglia);
            ingrediente = new Ingrediente(nome, valoreCosto, valoreQuantita, unitaMisura, valoreSoglia, descrizione);
            System.err.println(nome + costo + quantita + unitaMisura + descrizione);

            try {
                repository.aggiungiIngrediente(ingrediente);
                repository.aggiornaListaIngredienti();
                setTornaIndietro();
            } catch (IOException e) {
                setMessaggioAggiungiIngrediente(e.getMessage());
            }
        }
    }

    public Boolean isInputAggiungiIngredienteValido(String nome, String costo, String quantita, String unitaMisura, String soglia, String descrizione) {
        if (nome == null || nome.isEmpty()) {
            // Handle the case where the 'nome' input is null or empty
            setMessaggioAggiungiIngrediente("Il nome non può essere vuoto");
            return false;
        }
        if (costo == null || Float.parseFloat(costo) < 0) {
            // Handle the case where the 'costo' input is null or negative
            setMessaggioAggiungiIngrediente("Il costo deve essere maggiore o uguale a zero");
            return false;
        }
        if (quantita == null || Float.parseFloat(quantita) < 0) {
            // Handle the case where the 'quantita' input is null or negative
            setMessaggioAggiungiIngrediente("La quantità deve essere maggiore o uguale a zero");
            return false;
        }
        if (unitaMisura == null || unitaMisura.isEmpty()) {
            // Handle the case where the 'unitaMisura' input is null or empty
            setMessaggioAggiungiIngrediente("L'unità di misura non può essere vuota");
            return false;
        }
        if (soglia == null || Float.parseFloat(soglia) < 0) {
            // Handle the case where the 'soglia' input is null or negative
            setMessaggioAggiungiIngrediente("La soglia deve essere maggiore o uguale a zero");
            return false;
        }

        if (Float.parseFloat(soglia) > Float.parseFloat(quantita)) {
            // Handle the case where the 'soglia' input is null or negative
            setMessaggioAggiungiIngrediente("La soglia non può essere più grande della quantità inserita");
            return false;
        }

        if (descrizione == null || descrizione.isEmpty()) {
            // Handle the case where the 'descrizione' input is null or empty
            setMessaggioAggiungiIngrediente("La descrizione non può essere vuota");
            return false;
        }
        return true;
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
        return !getMessaggioAggiungiIngrediente().equals("");
    }

    public void cancellaMessaggioAggiungiIngrediente() {
        messaggioAggiungiIngrediente.setValue("");
    }
}
