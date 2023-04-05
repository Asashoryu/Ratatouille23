package com.rat.ratatouille23.viewmodel;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rat.ratatouille23.eccezioni.rat.creadipendente.AggiungiDipendenteException;
import com.rat.ratatouille23.eccezioni.rat.creadipendente.RuoloNonTrovatoException;
import com.rat.ratatouille23.model.Allergene;
import com.rat.ratatouille23.model.Dipendente;
import com.rat.ratatouille23.repository.Repository;

import java.util.ArrayList;

public class AggiungiDipendenteViewModel extends ViewModel {
    Repository repository;

    Dipendente dipendente;

    private final String termineSupervisore = "Supervisore";
    private final String termineAddettoSala = "Addetto sala";
    private final String termineAddettoCucina = "Addetto cucina";

    ArrayList<Allergene> listaAllergeniSelezionati;

    public final ObservableField<String> selectedRuolo = new ObservableField<>("");

    public MutableLiveData<Boolean> tornaIndietro = new MutableLiveData<>(false);

    public MutableLiveData<String> messaggioAggiungiDipendente = new MutableLiveData<>("");

    public AggiungiDipendenteViewModel() {
        repository = Repository.getInstance();
        repository.setAggiungiDipendenteViewModel(this);
    }

    public void aggiungiDipendente(String nome, String cognome, String username, String ruolo, String password) {
        try {
            dipendente = null;
            dipendente = new Dipendente(nome, cognome, username, validaRuolo(ruolo), password, false, null);
            repository.aggiungiDipendente(dipendente);
            System.err.println(nome + cognome + username + ruolo + password);
            setTornaIndietro();
        } catch (AggiungiDipendenteException e) {
            setMessaggioAggiungiDipendente(e.getMessage());
        }
    }

    public Dipendente.Ruolo validaRuolo(String ruolo) throws RuoloNonTrovatoException {
        if (ruolo.equals(termineSupervisore)) {
            return Dipendente.Ruolo.SUPERVISORE;
        }
        else if (ruolo.equals(termineAddettoSala)) {
            return Dipendente.Ruolo.ADDETTOSALA;
        }
        else if (ruolo.equals(termineAddettoCucina)) {
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

    public ArrayList<String> getRuoloNames() {
        ArrayList<String> ruoloNames = new ArrayList<>();
        ruoloNames.add(termineSupervisore);
        ruoloNames.add(termineAddettoSala);
        ruoloNames.add(termineAddettoCucina);
        return ruoloNames;
    }

    public ObservableField<String> getSelectedRuolo() {
        return selectedRuolo;
    }

    public String getSelectedRuoloName() {
        return selectedRuolo.get();
    }
}
