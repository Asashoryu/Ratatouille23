package com.rat.ratatouille23.viewmodel;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rat.ratatouille23.eccezioni.rat.Ratatouille23Exception;
import com.rat.ratatouille23.eccezioni.rat.menu.CampiPortataVuotiException;
import com.rat.ratatouille23.model.Allergene;
import com.rat.ratatouille23.model.Categoria;
import com.rat.ratatouille23.model.Portata;
import com.rat.ratatouille23.repository.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class AggiungiPortataViewModel extends ViewModel {
    Repository repository;

    Portata portata;

    ArrayList<Allergene> listaAllergeniSelezionati;

    public MutableLiveData<ArrayList<Categoria>> listaCategorie = new MutableLiveData<ArrayList<Categoria>>();

    public MutableLiveData<Boolean> tornaIndietro = new MutableLiveData<>(false);

    public MutableLiveData<String> messaggioAggiungiPortata = new MutableLiveData<>("");

    public final ObservableField<String> selectedCategory = new ObservableField<>("");

    public AggiungiPortataViewModel() {
        repository = Repository.getInstance();
        repository.setAggiungiPortataViewModel(this);
    }

    public void aggiungiPortata(String nome, String costo, String categoria, String descrizione) {
        try {
            portata = null;
            checkPortata(nome,costo,categoria);
            repository.aggiungiPortataAllaCategoria(new Portata(nome, Float.parseFloat(costo), descrizione, listaAllergeniSelezionati), categoria);
            System.err.println(nome + costo + categoria + descrizione);
            setTornaIndietro();

        } catch (Ratatouille23Exception | IOException e) {
            setMessaggioAggiungiPortata(e.getMessage());
        }
    }

    public void checkPortata(String nome, String costo, String categoria) throws CampiPortataVuotiException {
        boolean controlloStringhe = true;
        Set<String> stringSet = new HashSet<>();
        stringSet.add(nome);
        stringSet.add(costo);
        stringSet.add(categoria);

        for (String string: stringSet) {
            if (string == null || string.trim().isEmpty()) {
                controlloStringhe = false;
                break;
            }
        }

        if (!controlloStringhe) {
            throw new CampiPortataVuotiException();
        }
    }

    public void setTornaIndietro() {
        tornaIndietro.setValue(true);
    }

    public void setFalseTornaIndietro() {
        tornaIndietro.setValue(false);
    }

    public void setMessaggioAggiungiPortata(String nuovoMessaggioAggiungiPortata) {
        messaggioAggiungiPortata.setValue(nuovoMessaggioAggiungiPortata);
    }

    public String getMessaggioAggiungiPortata() {
        return messaggioAggiungiPortata.getValue();
    }

    public Boolean isNuovoMessaggioAggiungiPortata() {
        return getMessaggioAggiungiPortata() != "";
    }

    public void cancellaMessaggioAggiungiPortata() {
        messaggioAggiungiPortata.setValue("");
    }

    public ArrayList<String> getCategoryNames() {
        ArrayList<String> categoryNames = new ArrayList<>();
        for (Categoria categoria : repository.getMenu().getCategorie()) {
            categoryNames.add(categoria.getNome());
        }
        return categoryNames;
    }

    public ObservableField<String> getSelectedCategory() {
        return selectedCategory;
    }

    public String getSelectedCategoryName() {
        return selectedCategory.get();
    }
}
