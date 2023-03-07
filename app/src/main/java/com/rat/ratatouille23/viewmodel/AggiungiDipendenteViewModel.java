package com.rat.ratatouille23.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rat.ratatouille23.eccezioni.AggiungiDipendenteException;
import com.rat.ratatouille23.eccezioni.PersonalizzaMenuException;
import com.rat.ratatouille23.eccezioni.RuoloNonTrovatoException;
import com.rat.ratatouille23.model.Allergene;
import com.rat.ratatouille23.model.Dipendente;
import com.rat.ratatouille23.repository.Repository;

import java.util.ArrayList;

public class AggiungiDipendenteViewModel extends ViewModel {
    Repository repository;

    Dipendente dipendente;

    ArrayList<Allergene> listaAllergeniSelezionati;

    public MutableLiveData<Boolean> tornaIndietro = new MutableLiveData<>(false);

    public MutableLiveData<String> messaggioAggiungiDipendente = new MutableLiveData<>("");

    public AggiungiDipendenteViewModel() {
        repository = Repository.getInstance();
        repository.setAggiungiDipendenteViewModel(this);
    }

    public void aggiungiDipendente(String nome, String cognome, String username, String ruolo, String password) {
        try {
            dipendente = null;
            dipendente = new Dipendente(nome, cognome, username, validaRuolo(ruolo), password, false);
            repository.aggiungiDipendente(dipendente);
            System.err.println(nome + cognome + username + ruolo + password);
            setTornaIndietro();
        } catch (AggiungiDipendenteException e) {
            setMessaggioAggiungiDipendente(e.getMessage());
        }
    }

    public Dipendente.Ruolo validaRuolo(String ruolo) throws RuoloNonTrovatoException {
        if (ruolo.equals("supervisore")) {
            return Dipendente.Ruolo.SUPERVISORE;
        }
        else if (ruolo.equals("addettosala")) {
            return Dipendente.Ruolo.ADDETTOSALA;
        }
        else if (ruolo.equals("addettocucina")) {
            return Dipendente.Ruolo.ADDETTOCUCINA;
        }
        else {
            throw new RuoloNonTrovatoException();
        }
    }

    public void setTornaIndietro() {
        tornaIndietro.setValue(true);
    }

    public void setFalseTornaIndietro() {
        tornaIndietro.setValue(false);
    }

    public void setMessaggioAggiungiDipendente(String nuovoMessaggioAggiungiDipendente) {
        messaggioAggiungiDipendente.setValue(nuovoMessaggioAggiungiDipendente);
    }

    public String getMessaggioAggiungiDipendente() {
        return messaggioAggiungiDipendente.getValue();
    }

    public Boolean isNuovoMessaggioAggiungiDipendente() {
        return getMessaggioAggiungiDipendente() != "";
    }

    public void cancellaMessaggioAggiungiDipendente() {
        messaggioAggiungiDipendente.setValue("");
    }
}
