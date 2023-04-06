package com.rat.ratatouille23.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rat.ratatouille23.eccezioni.rat.associaingredienti.QuantiaIngredienteException;
import com.rat.ratatouille23.eccezioni.rat.associaingredienti.QuantitaIngredienteNegativaException;
import com.rat.ratatouille23.eccezioni.rat.associaingredienti.QuantitaIngredienteNullException;
import com.rat.ratatouille23.model.Ingrediente;
import com.rat.ratatouille23.repository.Repository;

import java.io.IOException;

public class IndicaQuantitaViewModel extends ViewModel {

    Repository repository;

    Ingrediente ingrediente;

    public MutableLiveData<Boolean> tornaIndietro = new MutableLiveData<>(false);

    public MutableLiveData<String> messaggioIndicaQuantita = new MutableLiveData<>("");

    public IndicaQuantitaViewModel() {
        repository = Repository.getInstance();
        repository.setIndicaQuantitaViewModel(this);

        ingrediente = repository.getIngredienteSelezionato();
    }

    public void aggiungiIngredienteAlProdotto(String quantita) {
        try {
            checkQuantitaNull(quantita);
            checkQuantitaNegativa(Float.parseFloat(quantita));
            repository.aggiungiIngredienteAllaPortataSelezionata(ingrediente, Float.parseFloat(quantita));
            setTornaIndietro();
        } catch (IOException | QuantiaIngredienteException e) {
            setMessaggioIndicaQuantita(e.getMessage());
        }
    }

    public void checkQuantitaNull (String quantita) throws QuantitaIngredienteNullException {
        if (quantita.isEmpty()) {
            throw new QuantitaIngredienteNullException();
        }
    }

    public void checkQuantitaNegativa (float quantita) throws QuantitaIngredienteNegativaException {
        if (quantita <= 0)
        {
            throw new QuantitaIngredienteNegativaException();
        }
    }

    public String getUnitaMisuraIngrediente() {
        return ingrediente.getUnitaMisura();
    }


    public void setTornaIndietro() {
        tornaIndietro.setValue(true);
    }

    public void setFalseTornaIndietro() {
        tornaIndietro.setValue(false);
    }

    public void setMessaggioIndicaQuantita(String nuovoMessaggioIndicaQuantita) {
        messaggioIndicaQuantita.setValue(nuovoMessaggioIndicaQuantita);
    }

    public String getMessaggioIndicaQuantita() {
        return messaggioIndicaQuantita.getValue();
    }

    public Boolean isNuovoMessaggioIndicaQuantita() {
        return getMessaggioIndicaQuantita() != "";
    }

    public void cancellaMessaggioIndicaQuantita() {
        messaggioIndicaQuantita.setValue("");
    }
}
