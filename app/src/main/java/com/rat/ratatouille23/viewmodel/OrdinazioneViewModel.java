package com.rat.ratatouille23.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rat.ratatouille23.model.Categoria;
import com.rat.ratatouille23.model.Ingrediente;
import com.rat.ratatouille23.model.IngredientePortata;
import com.rat.ratatouille23.model.Menu;
import com.rat.ratatouille23.model.Ordinazione;
import com.rat.ratatouille23.model.Portata;
import com.rat.ratatouille23.model.PortataOrdine;
import com.rat.ratatouille23.model.Tavolo;
import com.rat.ratatouille23.repository.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class OrdinazioneViewModel extends ViewModel {

    private Repository repository;

    private Tavolo tavolo;

    private Menu menu;

    private ArrayList<Ordinazione> ordinazioniAperte;

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

        ordinazioniAperte = repository.getOrdinazioni();

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
        if (isIngredientiDisponibiliPerPortata(portata)) {
            ordinazione.incrementaPortata(portata);
            aggiornaCostoTotaleConto();
            System.err.println("aggiornamento portata al conto fatto");
        }
        else {
            setMessaggioOrdinazione("Ingredienti insufficienti per questa portata");
        }
    }

    public boolean isIngredientiDisponibiliPerPortata(Portata portata) {
        Set<Ordinazione> allOrdinazioni = new HashSet<>(ordinazioniAperte);
        allOrdinazioni.add(ordinazione);
        for (IngredientePortata ingredientePortata : portata.getIngredientiPortata()) {
            Ingrediente ingrediente = ingredientePortata.getIngrediente();
            float quantitaIngredienteDisponibile = ingrediente.getQuantita();
            for (Ordinazione ordinazioneAperta : allOrdinazioni) {
                for (PortataOrdine portataOrdine : ordinazioneAperta.getPortateOrdine()) {
                    if (portataOrdine.getPortata().equals(portata)) {
                        float quantitaUsata = portataOrdine.getQuantitaIngredienteUsata(ingrediente);
                        quantitaIngredienteDisponibile -= quantitaUsata;
                        if (quantitaIngredienteDisponibile < 0) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
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
