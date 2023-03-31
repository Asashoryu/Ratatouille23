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

    public ArrayList<Portata> getPortate() {
        ArrayList<Portata> portate = new ArrayList<>();
        for (Categoria categoria : categorie) {
            portate.addAll(categoria.getPortate());
        }
        return portate;
    }


    public ArrayList<Categoria> getCategorie() {
        return categorie;
    }

    public void setCategorie(ArrayList<Categoria> categorie) {
        this.categorie = categorie;
    }
}
