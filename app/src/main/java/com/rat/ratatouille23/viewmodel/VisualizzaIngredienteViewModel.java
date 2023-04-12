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

    public void setNomeEditable (EditText nome) {
        nome.setEnabled(!nome.isEnabled());
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
