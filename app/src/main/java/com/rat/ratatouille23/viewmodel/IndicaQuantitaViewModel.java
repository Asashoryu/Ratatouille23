package com.rat.ratatouille23.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rat.ratatouille23.eccezioni.rat.associaingredienti.QuantitaIngredienteException;
import com.rat.ratatouille23.eccezioni.rat.associaingredienti.QuantitaIngredienteNegativaException;
import com.rat.ratatouille23.eccezioni.rat.associaingredienti.QuantitaIngredienteNullException;
import com.rat.ratatouille23.model.Ingrediente;
import com.rat.ratatouille23.repository.IngredientiRepository;
import com.rat.ratatouille23.repository.Repository;

import java.io.IOException;

public class IndicaQuantitaViewModel extends ViewModel {

    Repository repository;

    IngredientiRepository ingredientiRepository;

    Ingrediente ingrediente;

    public MutableLiveData<Boolean> tornaIndietro = new MutableLiveData<>(false);

    public MutableLiveData<String> messaggioIndicaQuantita = new MutableLiveData<>("");

    public IndicaQuantitaViewModel() {
        repository = Repository.getInstance();
        Repository.indicaQuantitaViewModel = this;
        ingredientiRepository = new IngredientiRepository();

        ingrediente = repository.getIngredienteSelezionato();
    }

    public void aggiungiIngredienteAlProdotto(String quantita) {
        try {
            checkQuantitaNull(quantita);
            checkQuantitaNegativa(Float.parseFloat(quantita));
            aggiungiIngredienteAllaPortataSelezionata(ingrediente, Float.parseFloat(quantita));
            setTornaIndietro();
        } catch (IOException | QuantitaIngredienteException e) {
            setMessaggioIndicaQuantita(e.getMessage());
        }
    }

    public void aggiungiIngredienteAllaPortataSelezionata(Ingrediente ingrediente, Float quantita) throws IOException {
        ingredientiRepository.associaIngridientToDishBackend(quantita, ingrediente.getNome(), repository.getPortataSelezionata().getNome());
        Repository.associaIngredientiViewModel.aggiungiIngredienteAllaPortata(ingrediente, quantita);
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
