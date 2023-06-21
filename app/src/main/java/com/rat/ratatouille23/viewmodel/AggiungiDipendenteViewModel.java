package com.rat.ratatouille23.viewmodel;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rat.ratatouille23.eccezioni.rat.creadipendente.AggiungiDipendenteException;
import com.rat.ratatouille23.eccezioni.rat.creadipendente.CammpiDipendenteVuotiException;
import com.rat.ratatouille23.eccezioni.rat.creadipendente.RuoloNonTrovatoException;
import com.rat.ratatouille23.model.Allergene;
import com.rat.ratatouille23.model.Dipendente;
import com.rat.ratatouille23.repository.LoginRepository;
import com.rat.ratatouille23.repository.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class AggiungiDipendenteViewModel extends ViewModel {
    Repository repository;

    LoginRepository loginRepository;

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
        Repository.aggiungiDipendenteViewModel = this;
        loginRepository = new LoginRepository();
    }

    public void aggiungiDipendente(String nome, String cognome, String username, String ruolo, String password) {
        try {
            dipendente = null;
            checkCampi(nome,cognome,username,password);
            dipendente = new Dipendente(nome, cognome, username, validaRuolo(ruolo), password, false, null);
            loginRepository.creaDipendenteBackend(dipendente.getUsername(), dipendente.getPassword(), dipendente.getNome(), dipendente.getCognome(), dipendente.getRuolo().toString(), String.valueOf((dipendente.getReimpostata())));
            System.err.println(nome + cognome + username + ruolo + password);
            setTornaIndietro();
        } catch (AggiungiDipendenteException | IOException e) {
            setMessaggioAggiungiDipendente(e.getMessage());
        }
    }

    public Dipendente.Ruolo validaRuolo(String ruolo) throws RuoloNonTrovatoException {
        switch (ruolo) {
            case termineSupervisore:
                return Dipendente.Ruolo.SUPERVISORE;
            case termineAddettoSala:
                return Dipendente.Ruolo.ADDETTOSALA;
            case termineAddettoCucina:
                return Dipendente.Ruolo.ADDETTOCUCINA;
            default:
                throw new RuoloNonTrovatoException();
        }
    }

    public void checkCampi(String nome, String cognome, String username, String password) throws CammpiDipendenteVuotiException {
        boolean controlloStringhe = true;
        Set<String> stringSet = new HashSet<>();
        stringSet.add(nome);
        stringSet.add(cognome);
        stringSet.add(username);
        stringSet.add(password);

        for (String string: stringSet) {
            if (string == null || string.trim().isEmpty()) {
                controlloStringhe = false;
                break;
            }
        }
        if (!controlloStringhe) {
            throw new CammpiDipendenteVuotiException();
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
