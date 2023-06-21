package com.rat.ratatouille23.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rat.ratatouille23.DTO.Ordered_Dish_DTO;
import com.rat.ratatouille23.model.Categoria;
import com.rat.ratatouille23.model.Ingrediente;
import com.rat.ratatouille23.model.IngredientePortata;
import com.rat.ratatouille23.model.Menu;
import com.rat.ratatouille23.model.Ordinazione;
import com.rat.ratatouille23.model.Portata;
import com.rat.ratatouille23.model.PortataOrdine;
import com.rat.ratatouille23.model.Tavolo;
import com.rat.ratatouille23.repository.OrdinazioniRepository;
import com.rat.ratatouille23.repository.Repository;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class OrdinazioneViewModel extends ViewModel {

    private Repository repository;

    private OrdinazioniRepository ordinazioniRepository;

    private Tavolo tavolo;

    private Menu menu;

    private ArrayList<Ordinazione> ordinazioniTutte;

    private Ordinazione ordinazione;

    public MutableLiveData<ArrayList<Categoria>> listaCategorie = new MutableLiveData<ArrayList<Categoria>>();

    public MutableLiveData<ArrayList<Portata>> listaPortate = new MutableLiveData<ArrayList<Portata>>();

    public MutableLiveData<ArrayList<PortataOrdine>> listaPortateConto = new MutableLiveData<ArrayList<PortataOrdine>>();

    public MutableLiveData<Boolean> tornaIndietro = new MutableLiveData<>(false);

    public MutableLiveData<Float> costoTotaleConto = new MutableLiveData<>(0.0f);

    public MutableLiveData<String> messaggioOrdinazione = new MutableLiveData<>("");

    public OrdinazioneViewModel() {
        repository = Repository.getInstance();
        Repository.ordinazioneViewModel = this;
        ordinazioniRepository = new OrdinazioniRepository();

        menu = repository.getMenu();

        tavolo = repository.getTavoloSelezionato();

        ordinazioniTutte = repository.getOrdinazioni();

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
            System.err.println("Tento di stampare che non ci sono gli ingredienti per la portata");
            setMessaggioOrdinazione("Ingredienti insufficienti per questa portata");
        }
    }

    public boolean isIngredientiDisponibiliPerPortata(Portata portata) {
        Set<Ordinazione> allOrdinazioni = new HashSet<>(ordinazioniTutte);
        allOrdinazioni.add(ordinazione);
        ordinazione.incrementaPortata(portata);
        for (IngredientePortata ingredientePortata : portata.getIngredientiPortata()) {
            Ingrediente ingrediente = ingredientePortata.getIngrediente();
            System.err.println("Ingrediente : " +ingrediente.getNome() + " " + ingrediente.getQuantita() + " " + ingrediente.getSoglia());
            float quantitaIngredienteDisponibile = ingrediente.getQuantita();
            for (Ordinazione unaOrdinazione : allOrdinazioni) {
                if (!unaOrdinazione.getChiusa()) {
                    for (PortataOrdine portataOrdine : unaOrdinazione.getPortateOrdine()) {
                        if (portataOrdine.getPortata().getIngredientiPortata().contains(ingredientePortata)) {
                            float quantitaUsata = portataOrdine.getQuantitaIngredienteUsata(ingrediente);
                            quantitaIngredienteDisponibile -= quantitaUsata;
                            if (quantitaIngredienteDisponibile < 0.0f) {
                                ordinazione.decrementaPortata(portata);
                                return false;
                            }
                        }
                    }
                }
            }
        }
        ordinazione.decrementaPortata(portata);
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
            if (isOrdinazioneInputValido()) {
                salvaOrdinazioneTavoloSelezionato();
                tavolo.occupaTavoloConOrdinazione(ordinazione);
                ordinazioniTutte.add(ordinazione);
                setTornaIndietro();
            }
        } catch (IOException e) {
            System.err.println("ordinazione messaggio: "+ e.getMessage());
            setMessaggioOrdinazione(e.getMessage());
        }
    }

    public void salvaOrdinazioneTavoloSelezionato() throws IOException {
        int nuovoId;
        Tavolo tavoloSelezionato = repository.getTavoloSelezionato();
        if (tavoloSelezionato.getOrdinazione().getId() == -1) {
            nuovoId = ordinazioniRepository.getNewOrdinazioneIdBackend();
            tavoloSelezionato.getOrdinazione().setId(nuovoId);
        }
        else {
            nuovoId = tavoloSelezionato.getOrdinazione().getId();
            ordinazioniRepository.deleteContoBackend(nuovoId);
        }
        System.out.println("Il nuovo id ottenuto è " + nuovoId);
        ordinazioniRepository.saveContoBackend(nuovoId, getMinutaggioAdesso(), tavoloSelezionato.getOrdinazione().getCostoTotalePortateOrdine(), false, tavoloSelezionato.getId(), convertPortataOrdineToOrdered_Dish_DTO(tavoloSelezionato.getOrdinazione().getPortateOrdine(), nuovoId));
    }

    public List<Ordered_Dish_DTO> convertPortataOrdineToOrdered_Dish_DTO(ArrayList<PortataOrdine> portataOrdineList, int contoId) {
        List<Ordered_Dish_DTO> orderedDishDTOList = new ArrayList<>();

        for (PortataOrdine portataOrdine : portataOrdineList) {
            Ordered_Dish_DTO orderedDishDTO = new Ordered_Dish_DTO(
                    portataOrdine.getQuantita(),
                    contoId,
                    portataOrdine.getPortata().getNome()
            );
            orderedDishDTOList.add(orderedDishDTO);
        }

        return orderedDishDTOList;
    }

    public int getMinutaggioAdesso() {
        return Integer.parseInt(String.valueOf(Instant.now().getEpochSecond()));
    }

    private boolean isOrdinazioneInputValido() {
        if (ordinazione == null || ordinazione.getPortateOrdine().isEmpty()) {
            setMessaggioOrdinazione("L'ordinazione non può essere vuota");
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

    public void setMessaggioOrdinazione(String nuovoMessaggioOrdinazione) {
        messaggioOrdinazione.setValue(nuovoMessaggioOrdinazione);
    }

    public String getMessaggioOrdinazione() {
        return messaggioOrdinazione.getValue();
    }


    public Boolean isNuovoMessaggioOrdinazione() {
        return !getMessaggioOrdinazione().equals("");
    }

    public void cancellaMessaggioOrdinazione() {
        messaggioOrdinazione.setValue("");
    }

}
