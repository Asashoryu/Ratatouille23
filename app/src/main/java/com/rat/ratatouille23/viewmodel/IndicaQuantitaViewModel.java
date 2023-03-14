package com.rat.ratatouille23.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rat.ratatouille23.model.Ingrediente;
import com.rat.ratatouille23.repository.Repository;

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
        repository.aggiungiIngredienteAllaPortataSelezionata(ingrediente, Float.parseFloat(quantita));
        setTornaIndietro();
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
