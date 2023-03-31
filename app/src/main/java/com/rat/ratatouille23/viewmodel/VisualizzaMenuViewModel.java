package com.rat.ratatouille23.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rat.ratatouille23.model.Categoria;
import com.rat.ratatouille23.model.Menu;
import com.rat.ratatouille23.model.Portata;
import com.rat.ratatouille23.repository.Repository;

import java.io.IOException;
import java.util.ArrayList;

public class VisualizzaMenuViewModel extends ViewModel {
    Repository repository;

    Menu menu;

    public MutableLiveData<ArrayList<Categoria>> listaCategorie = new MutableLiveData<ArrayList<Categoria>>();

    public MutableLiveData<ArrayList<Portata>> listaPortate = new MutableLiveData<ArrayList<Portata>>();

    public MutableLiveData<Boolean> vaiAdAggiungiPortata = new MutableLiveData<>(false);

    public MutableLiveData<String> messaggioVisualizzaMenu = new MutableLiveData<>("");

    public VisualizzaMenuViewModel() {
        repository = Repository.getInstance();
        repository.setVisualizzaMenuViewModel(this);

        menu = repository.getMenu();
        aggiornaListaCategorie();
    }

    public void aggiornaListaCategorie() {
        listaCategorie.setValue(menu.getCategorie());
        menu.getCategorie().forEach(categoria -> {System.out.println(categoria.getNome());});

    }
    public void aggiornaListaPortate(Categoria categoriaSelezionata) {
        listaPortate.setValue(menu.getPortateDellaCategoria(categoriaSelezionata));
    }

    public void impostaPortataSelezionata(Portata portata) {
        repository.setPortataSelezionata(portata);
    }

    public void setMessaggioVisualizzaMenu(String nuovoMessaggioVisualizzaMenu) {
        messaggioVisualizzaMenu.setValue(nuovoMessaggioVisualizzaMenu);
    }

    public String getMessaggioVisualizzaMenu() {
        return messaggioVisualizzaMenu.getValue();
    }


    public Boolean isNuovoMessaggioVisualizzaMenu() {
        return getMessaggioVisualizzaMenu() != "";
    }

    public void cancellaMessaggioVisualizzaMenu() {
        messaggioVisualizzaMenu.setValue("");
    }
}
