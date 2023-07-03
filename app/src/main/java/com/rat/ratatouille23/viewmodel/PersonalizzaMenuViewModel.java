package com.rat.ratatouille23.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rat.ratatouille23.model.Categoria;
import com.rat.ratatouille23.model.Portata;
import com.rat.ratatouille23.model.Menu;
import com.rat.ratatouille23.repository.Repository;

import java.util.ArrayList;
import java.util.Collections;

public class PersonalizzaMenuViewModel extends ViewModel {

    Repository repository;

    Menu menu;

    Categoria tutti;

    ArrayList<Categoria> categorie;

    String nomeTutti = "Tutti";

    public MutableLiveData<ArrayList<Categoria>> listaCategorie = new MutableLiveData<ArrayList<Categoria>>();

    public MutableLiveData<ArrayList<Portata>> listaPortate = new MutableLiveData<ArrayList<Portata>>();

    public MutableLiveData<Boolean> isCliccatoOrdina = new MutableLiveData<>(false);

    public MutableLiveData<Boolean> vaiAdAggiungiPortata = new MutableLiveData<>(false);

    public MutableLiveData<Boolean> tornaIndietro = new MutableLiveData<>(false);

    public MutableLiveData<String> messaggioPersonalizzaMenu = new MutableLiveData<>("");

    public PersonalizzaMenuViewModel() {
        repository = Repository.getInstance();
        Repository.personalizzaMenuViewModel = this;

        menu = repository.getMenu();

        categorie = menu.getCategorie();


        tutti = new Categoria(nomeTutti);

        categorie.add(0,tutti);

        tutti.getPortate().forEach(portata -> {System.err.println(portata.getNome());});

        Collections.shuffle(tutti.getPortate());

        aggiornaListaCategorie();
        aggiornaListaPortate(tutti);
    }

    public void aggiornaListaCategorie() {
        listaCategorie.setValue(categorie);
        menu.getCategorie().forEach(categoria -> {System.out.println(categoria.getNome());});

    }

    public void aggiornaListaPortate(Categoria categoriaSelezionata) {
        if (categoriaSelezionata == tutti || categoriaSelezionata == null) {
            tutti.setPortate(menu.getTuttePortate());
            listaPortate.setValue(menu.getPortateDellaCategoria(tutti));
        }
        else {
            listaPortate.setValue(menu.getPortateDellaCategoria(categoriaSelezionata));
        }
    }

    public void ordinaEaggiornaCategoriaTutti() {
        selectionSortPortate(tutti.getPortate());
        listaPortate.setValue(tutti.getPortate());
    }

    public static void selectionSortPortate(ArrayList<Portata> portate) {
        for (int i = 0; i < portate.size() - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < portate.size(); j++) {
                if (portate.get(j).getNome().compareTo(portate.get(minIndex).getNome()) < 0) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                Portata temp = portate.get(i);
                portate.set(i, portate.get(minIndex));
                portate.set(minIndex, temp);
            }
        }
    }


    public void impostaPortataSelezionata(Portata portata) {
        repository.setPortataSelezionata(portata);
    }

    public void setIsCliccatoOrdina() {
        isCliccatoOrdina.setValue(true);
        isCliccatoOrdina.setValue(false);
    }

    public void setVaiAdAggiungiPortata() {
        vaiAdAggiungiPortata.setValue(true);
    }

    public void setFalseVaiAdAggiungiPortata() {
        vaiAdAggiungiPortata.setValue(false);
    }

    public void setTornaIndietro() {
        tornaIndietro.setValue(true);
    }

    public void setFalseTornaIndietro() {
        tornaIndietro.setValue(false);
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
