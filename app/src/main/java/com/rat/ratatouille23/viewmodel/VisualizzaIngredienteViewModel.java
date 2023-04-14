package com.rat.ratatouille23.viewmodel;

import android.widget.EditText;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rat.ratatouille23.model.Ingrediente;
import com.rat.ratatouille23.repository.Repository;

import java.io.IOException;

public class VisualizzaIngredienteViewModel extends ViewModel {
    Repository repository;

    Ingrediente ingrediente;

    public MutableLiveData<Boolean> tornaIndietro = new MutableLiveData<>(false);

    public MutableLiveData<String> messaggioVisualizzaIngrediente = new MutableLiveData<>("");

    public VisualizzaIngredienteViewModel() {
        repository = Repository.getInstance();
        repository.setVisualizzaIngredienteViewModel(this);

        ingrediente = repository.getIngredienteSelezionato();
    }

    public void eliminaIngrediente() {
        try {
            repository.eliminaIngredienteSelezionato();
        } catch (IOException e) {
            setMessaggioVisualizzaIngrediente(e.getMessage());
        }
        setTornaIndietro();
    }

    public void modificaIngrediente(String nome, String costo, String quantita, String descrizione) {
        Float valoreCosto = Float.parseFloat(costo);
        Float valoreQuantita = Float.parseFloat(quantita);

        Ingrediente newIng = new Ingrediente(nome,valoreCosto,valoreQuantita,ingrediente.getUnitaMisura(),ingrediente.getSoglia(),descrizione);

        try {
            repository.modificaIngrediente(newIng);
        } catch (IOException e) {
            setMessaggioVisualizzaIngrediente(e.getMessage());
        }
        setTornaIndietro();
    }

    public void setNomeEditable (EditText nome) {
        nome.setEnabled(!nome.isEnabled());
    }

    public void setCostoEditable (EditText costo) {
        costo.setEnabled(!costo.isEnabled());
    }

    public void setQuanitaEditable (EditText quantita) {
        quantita.setEnabled(!quantita.isEnabled());
    }
    public void setDescrizioneEditable (EditText descrizione) {
        descrizione.setEnabled(!descrizione.isEnabled());
    }

    public boolean nomeDiverso (String nome) {
        return (!nome.equals(ingrediente.getNome()));
    }

    public boolean costoDiverso (Float costo) {
        return (!costo.equals(ingrediente.getCosto()));
    }

    public boolean quantitaDiverso (Float quantita) {
        return (!quantita.equals(ingrediente.getQuantita()));
    }

    public boolean descrizioneDiverso (String descrizione) {
        return (!descrizione.equals(ingrediente.getDescrizione()));
    }

    public Ingrediente getIngrediente() {
        return ingrediente;
    }

    public void setTornaIndietro() {
        tornaIndietro.setValue(true);
    }

    public void setFalseTornaIndietro() {
        tornaIndietro.setValue(false);
    }

    public void setMessaggioVisualizzaIngrediente(String nuovoMessaggioVisualizzaIngrediente) {
        messaggioVisualizzaIngrediente.setValue(nuovoMessaggioVisualizzaIngrediente);
    }

    public String getMessaggioVisualizzaIngrediente() {
        return messaggioVisualizzaIngrediente.getValue();
    }

    public Boolean isNuovoMessaggioVisualizzaIngrediente() {
        return getMessaggioVisualizzaIngrediente() != "";
    }

    public void cancellaMessaggioVisualizzaIngrediente() {
        messaggioVisualizzaIngrediente.setValue("");
    }
}
