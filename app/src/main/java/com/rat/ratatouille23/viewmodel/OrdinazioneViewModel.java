package com.rat.ratatouille23.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rat.ratatouille23.model.Categoria;
import com.rat.ratatouille23.model.Menu;
import com.rat.ratatouille23.model.Ordinazione;
import com.rat.ratatouille23.model.Portata;
import com.rat.ratatouille23.model.PortataOrdine;
import com.rat.ratatouille23.model.Tavolo;
import com.rat.ratatouille23.repository.Repository;

import java.io.IOException;
import java.util.ArrayList;

public class OrdinazioneViewModel extends ViewModel {

    private Repository repository;

    private Tavolo tavolo;

    private Menu menu;

    private Ordinazione ordinazione;

    public MutableLiveData<ArrayList<Categoria>> listaCategorie = new MutableLiveData<ArrayList<Categoria>>();

    public MutableLiveData<ArrayList<Portata>> listaPortate = new MutableLiveData<ArrayList<Portata>>();

    public MutableLiveData<ArrayList<PortataOrdine>> listaPortateConto = new MutableLiveData<ArrayList<PortataOrdine>>();

    public MutableLiveData<Boolean> tornaIndietro = new MutableLiveData<>(false);

    public MutableLiveData<Float> costoTotaleConto = new MutableLiveData<>(0.0f);

    public MutableLiveData<String> messaggioOrdinazione = new MutableLiveData<>("");

    public OrdinazioneViewModel() {
        repository = Repository.getInstance();
        repository.setOrdinazioneViewModel(this);


        menu = repository.getMenu();

        tavolo = repository.getTavoloSelezionato();

        ordinazione = tavolo.getOrdinazione();

        ordinazione.setTavolo(repository.getTavoloSelezionato());
        aggiornaListaCategorie();
        aggiornaListaPortateConto();
        aggiornaCostoTotaleConto();
    }

    public void aggiornaListaCategorie() {
        listaCategorie.setValue(menu.getCategorie());
        menu.getCategorie().forEach(categoria -> {System.out.println(categoria.getNome());});
    }
    public void aggiornaListaPortate(Categoria categoriaSelezionata) {
        listaPortate.setValue(menu.getPortateDellaCategoria(categoriaSelezionata));
    }

    public void aggiornaListaPortateConto() {
        listaPortateConto.setValue(ordinazione.getPortateOrdine());
        System.err.println("aggiornamento lista portate conto fatto");
    }

    public void aggiungiPortataAllOrdinazione(Portata portata) {
        ordinazione.incrementaPortata(portata);
        aggiornaCostoTotaleConto();
        System.err.println("aggiornamento portata al conto fatto");
    }

    public void rimuoviPortataDaOrdinazione(PortataOrdine portataConto) {
        ordinazione.decrementaPortataOrdine(portataConto);
        aggiornaCostoTotaleConto();
    }

    public void aggiornaCostoTotaleConto() {
        costoTotaleConto.setValue(ordinazione.getCostoTotalePortateOrdine());
        System.err.println("aggiornamento costo totale conto fatto");
    }

    public void salvaOrdinazione() {

        try {
            repository.salvaOrdinazioneTavoloSelezionato();
        } catch (IOException e) {
            setMessaggioOrdinazione(e.getMessage());
        }
        tavolo.occupaTavoloConOrdinazione(ordinazione);
        setTornaIndietro();
    }

    public void setTornaIndietro() {
        tornaIndietro.setValue(true);
    }

    public void setFalseTornaIndietro() {
        tornaIndietro.setValue(false);
    }

    public void setMessaggioOrdinazione(String nuovoMessaggioOrdinazione) {
        messaggioOrdinazione.setValue(nuovoMessaggioOrdinazione);
    }

    public String getMessaggioOrdinazione() {
        return messaggioOrdinazione.getValue();
    }


    public Boolean isNuovoMessaggioOrdinazione() {
        return getMessaggioOrdinazione() != "";
    }

    public void cancellaMessaggioOrdinazione() {
        messaggioOrdinazione.setValue("");
    }

}
