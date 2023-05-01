package com.rat.ratatouille23.viewmodel;

import android.widget.EditText;
import android.widget.Spinner;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rat.ratatouille23.eccezioni.rat.menu.CampiPortataVuotiException;
import com.rat.ratatouille23.eccezioni.rat.menu.PersonalizzaMenuException;
import com.rat.ratatouille23.model.Allergene;
import com.rat.ratatouille23.model.Categoria;
import com.rat.ratatouille23.model.Portata;
import com.rat.ratatouille23.repository.Repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ModificaPortataViewModel extends ViewModel {

    Repository repository;

    Portata portata;

    String categoriaSelezionata;

    ArrayList<Allergene> listaAllergeniSelezionati;

    public int indiceCategoria = 0;

    String nomeTutti = "Tutti";

    public MutableLiveData<Boolean> tornaIndietro = new MutableLiveData<>(false);

    public MutableLiveData<String> messaggioModificaPortata = new MutableLiveData<>("");

    public ModificaPortataViewModel() {
        repository = Repository.getInstance();
        repository.setModificaPortataViewModel(this);
        portata = repository.getPortataSelezionata();
    }

    public void setNomeEditabile(EditText nome) {
        nome.setEnabled(!nome.isEnabled());
        nome.setText(portata.getNome());
    }

    public void setCostoEditable(EditText costo) {
        costo.setEnabled(!costo.isEnabled());
        costo.setText(portata.getCosto().toString());
    }

    public void setCategoriaEditable(Spinner categoria) {
        categoria.setEnabled(!categoria.isEnabled());
    }

    public void setAllergeniEditable(EditText allergeni) {
        allergeni.setEnabled(!allergeni.isEnabled());
        allergeni.setText(portata.getAllergeni());
    }

    public void setDescrizioneEditable(EditText descrizione) {
        descrizione.setEnabled(!descrizione.isEnabled());
        descrizione.setText(portata.getDescrizione());
    }

    public boolean nomeDiverso(String nome) {
        return (!nome.equals(portata.getNome()));
    }

    public boolean costoDiverso(Float costo) {
        return (!costo.equals(portata.getCosto()));
    }

    public boolean allergeniDiversi(String allergeni) {
        if (portata.getAllergeni() == null && allergeni.isEmpty()) {
            return false;   //NON sono diversi
        }
        return (!allergeni.equals(portata.getAllergeni()));
    }

    public boolean descrizioneDiversa(String descrizione) {
        if (portata.getDescrizione() == null && descrizione.isEmpty()) {
            return false;   //NON sono diversi
        }
        return (!descrizione.equals(portata.getDescrizione()));
    }

    public void modificaPortata (String nome, String costo, String categoria, String descrizione) {
        portata = null;
        try {
            portata = null;
            checkPortata(nome,costo,categoria);
            System.err.println(nome + costo + categoria + descrizione);
            setTornaIndietro();
        } catch (PersonalizzaMenuException e) {
            setMessaggioModificaPortata(e.getMessage());
        }
    }

    public void checkPortata(String nome, String costo, String categoria) throws CampiPortataVuotiException {
        boolean controlloStringhe = true;
        Set<String> stringSet = new HashSet<>();
        stringSet.add(nome);
        stringSet.add(costo);
        stringSet.add(categoria);

        for (String string : stringSet) {
            if (string == null || string.trim().isEmpty()) {
                controlloStringhe = false;
                break;
            }
        }
    }

    public ArrayList<String> getCategoryNames() {
        ArrayList<String> categoryNames = new ArrayList<>();
        for (Categoria categoria : repository.getMenu().getCategorie()) {
            if (!categoria.getNome().equals(nomeTutti)) {
                categoryNames.add(categoria.getNome());
            }
        }
        return categoryNames;
    }

    public Portata getPortata() {
        return portata;
    }

    public void setTornaIndietro() {
        tornaIndietro.setValue(true);
    }

    public void setFalseTornaIndietro() {
        tornaIndietro.setValue(false);
    }

    public void setMessaggioModificaPortata(String nuovoMessaggioAggiungiPortata) {
        messaggioModificaPortata.setValue(nuovoMessaggioAggiungiPortata);
    }

    public String getMessaggioModificaPortata() {
        return messaggioModificaPortata.getValue();
    }

    public Boolean isNuovoMessaggioModificaPortata() {
        return getMessaggioModificaPortata() != "";
    }

    public void cancellaMessaggioModificaPortata() {
        messaggioModificaPortata.setValue("");
    }

    public void setCategoriaSelezionata(String categoriaSelezionata) {
        this.categoriaSelezionata = categoriaSelezionata;
    }

    public String getCategoriaSelezionata() {
        return categoriaSelezionata;
    }

}
