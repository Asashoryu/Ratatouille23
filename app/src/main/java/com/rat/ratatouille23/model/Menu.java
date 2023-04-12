package com.rat.ratatouille23.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Menu {
    ArrayList<Categoria> categorie;

    public Menu() {
        categorie = new ArrayList<>();
    }

    public ArrayList<Portata> getPortateDellaCategoria(Categoria categoria) {
        int indiceCategoriaSelezionata = categorie.indexOf(categoria);
        return categorie.get(indiceCategoriaSelezionata).getPortate();
    }

    public ArrayList<Portata> getPortateOrdinateDellaCategoria(Categoria categoria) {
        ArrayList<Portata> portateOrdinate = getPortateDellaCategoria(categoria);
        Collections.sort(portateOrdinate,new Portata.NomeComparator());
        return portateOrdinate;
    }

    public ArrayList<Portata> getPortate() {
        ArrayList<Portata> portate = new ArrayList<>();
        for (Categoria categoria : categorie) {
            portate.addAll(categoria.getPortate());
        }
        return portate;
    }

    public ArrayList<Portata> ordinaPortate() {
        ArrayList<Portata> portateOrdinate = getPortate();
        Collections.sort(portateOrdinate,new Portata.NomeComparator());
        //portateOrdinate = insertionPortate(portateOrdinate);
        return portateOrdinate;
    }


    public ArrayList<Categoria> getCategorie() {
        return categorie;
    }

    public void setCategorie(ArrayList<Categoria> categorie) {
        this.categorie = categorie;
    }

    public ArrayList<Portata> insertionPortate (ArrayList<Portata> portate) {
        int i,j = 0;
        int n = portate.size();
        Portata portata;
        ArrayList<Portata> portateOrdinate = new ArrayList<>();
        ArrayList<Portata> portateClone = new ArrayList<>(portate);
        while (j < n) {
            portata = portateClone.get(0);
            for (i = 0; i < portateClone.size(); i++) {
                //ciao Moggy
                if (portata.getNome().compareTo(portateClone.get(i).getNome()) > 0) {
                    portata = portateClone.get(i);
                }
            }
            portateClone.remove(portata);
            portateOrdinate.add(j,portata);
            j++;
        }
        return portateOrdinate;
    }
}
