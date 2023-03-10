package com.rat.ratatouille23.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rat.ratatouille23.model.Menu;
import com.rat.ratatouille23.model.Ordinazione;
import com.rat.ratatouille23.model.Portata;
import com.rat.ratatouille23.model.Tavolo;
import com.rat.ratatouille23.repository.Repository;

import java.util.ArrayList;

public class VisualizzaContoViewModel extends ViewModel {
    Repository repository;

    Tavolo tavolo;

    Menu menu;

    Ordinazione ordinazione;

    public MutableLiveData<ArrayList<Portata>> listaPortateConto = new MutableLiveData<ArrayList<Portata>>();

    public MutableLiveData<Boolean> tornaIndietro = new MutableLiveData<>(false);

    public MutableLiveData<Float> costoTotaleConto = new MutableLiveData<>(0.0f);

    public VisualizzaContoViewModel() {
        repository = Repository.getInstance();
        repository.setVisualizzaContoViewModel(this);

        menu = repository.getMenu();
        tavolo = repository.getTavoloSelezionato();
        ordinazione = tavolo.getOrdinazione();
        aggiornaListaPortateConto();
        aggiornaCostoTotaleConto();
    }

    public void aggiornaListaPortateConto() {
        listaPortateConto.setValue(ordinazione.getPortate());
        System.err.println("aggiornamento lista portate conto fatto");
    }

    public void aggiornaCostoTotaleConto() {
        costoTotaleConto.setValue(ordinazione.getCostoTotalePortate());
        System.err.println("aggiornamento costo totale conto fatto");
    }

    public void salvaPDF() {
    }

    public void chiudiConto() {
    }

    public void setTornaIndietro() {
        tornaIndietro.setValue(true);
    }

    public void setFalseTornaIndietro() {
        tornaIndietro.setValue(false);
    }
}
