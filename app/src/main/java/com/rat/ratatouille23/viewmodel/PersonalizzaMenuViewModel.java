package com.rat.ratatouille23.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rat.ratatouille23.model.Categoria;
import com.rat.ratatouille23.model.Portata;
import com.rat.ratatouille23.model.Menu;
import com.rat.ratatouille23.model.Portata;
import com.rat.ratatouille23.repository.Repository;

import java.io.IOException;
import java.util.ArrayList;

public class PersonalizzaMenuViewModel extends ViewModel {

    Repository repository;

    Menu menu;

    Categoria tutti;

    ArrayList categorie;

    public MutableLiveData<ArrayList<Categoria>> listaCategorie = new MutableLiveData<ArrayList<Categoria>>();

    public MutableLiveData<ArrayList<Portata>> listaPortate = new MutableLiveData<ArrayList<Portata>>();

    public MutableLiveData<Boolean> isCliccato = new MutableLiveData<>(false);

    public MutableLiveData<Boolean> vaiAdAggiungiPortata = new MutableLiveData<>(false);

    public MutableLiveData<String> messaggioPersonalizzaMenu = new MutableLiveData<>("");

    public PersonalizzaMenuViewModel() {
        repository = Repository.getInstance();
        repository.setPersonalizzaMenuViewModel(this);

        menu = repository.getMenu();
        tutti = new Categoria("Tutti");
        categorie = new ArrayList<Categoria>(menu.getCategorie());
        categorie.add(0,tutti);

        aggiornaListaCategorie();
        aggiornaListaPortate(tutti);
    }

    public void aggiornaListaCategorie() {
        listaCategorie.setValue(categorie);
        menu.getCategorie().forEach(categoria -> {System.out.println(categoria.getNome());});

    }

    public void aggiornaListaPortate(Categoria categoriaSelezionata) {
        if (categoriaSelezionata.equals(tutti)) {
            listaPortate.setValue(menu.getPortate());   //getAll
        }
        else {
            listaPortate.setValue(menu.getPortateDellaCategoria(categoriaSelezionata));
        }
    }

    public void ordinaTutto() {
        for (Categoria categoria : menu.getCategorie()) {
                listaPortate.setValue(menu.getPortateOrdinateDellaCategoria(categoria));
        }
        listaPortate.setValue(menu.ordinaPortate());

    }

    public void impostaPortataSelezionata(Portata portata) {
        repository.setPortataSelezionata(portata);
    }

    public void setIsCliccato() {
        isCliccato.setValue(true);
        isCliccato.setValue(false);
    }

    public void setVaiAdAggiungiPortata() {
        vaiAdAggiungiPortata.setValue(true);
    }
    public void setFalseVaiAdAggiungiPortata() {
        vaiAdAggiungiPortata.setValue(false);
    }

    public void setMessaggioPersonalizzaMenu(String nuovoMessaggioPersonalizzaMenu) {
        messaggioPersonalizzaMenu.setValue(nuovoMessaggioPersonalizzaMenu);
    }

    public String getMessaggioPersonalizzaMenu() {
        return messaggioPersonalizzaMenu.getValue();
    }


    public Boolean isNuovoMessaggioPersonalizzaMenu() {
        return getMessaggioPersonalizzaMenu() != "";
    }

    public void cancellaMessaggioPersonalizzaMenu() {
        messaggioPersonalizzaMenu.setValue("");
    }
}
