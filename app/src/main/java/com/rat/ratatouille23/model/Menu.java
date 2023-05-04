package com.rat.ratatouille23.model;

import java.util.ArrayList;

public class Menu {
    ArrayList<Categoria> categorie;

    public Menu() {
        categorie = new ArrayList<>();
    }

    public ArrayList<Portata> getPortateDellaCategoria(Categoria categoria) {
        int indiceCategoriaSelezionata = categorie.indexOf(categoria);
        return categorie.get(indiceCategoriaSelezionata).getPortate();
    }


    public ArrayList<Portata> getTuttePortate() {
        ArrayList<Portata> portate = new ArrayList<>();
        for (Categoria categoria : categorie) {
            if (!categoria.getNome().equals("Tutti"))
                portate.addAll(categoria.getPortate());
        }
        return portate;
    }

    public ArrayList<Portata> getPortateOrdinateDellaCategoria(Categoria categoria) {
        ArrayList<Portata> portateOrdinate = getPortateDellaCategoria(categoria);
//        Collections.sort(portateOrdinate,new Portata.NomeComparator());
        portateOrdinate = selectionSortPortate(portateOrdinate);
        return portateOrdinate;
    }


    public ArrayList<Categoria> getCategorie() {
        return categorie;
    }

    public Categoria getCategoriaDaPortata (Portata portata) {
        for (Categoria categoria : categorie) {
            if (!categoria.getNome().equals("Tutti")) {
                for (Portata portataIndex : getPortateDellaCategoria(categoria)) {
                    if (portataIndex.equals(portata)) {
                        System.out.println(categoria.getNome());
                        return categoria;
                    }
                }
            }
        }
        return null;
    }

    public void setCategorie(ArrayList<Categoria> categorie) {
        this.categorie = categorie;
    }

    public static ArrayList<Portata> selectionSortPortate(ArrayList<Portata> portate) {
        ArrayList<Portata> sortedPortate = new ArrayList<>();
        while (portate.size() > 0) {
            Portata smallestPortata = portate.get(0);
            int smallestIndex = 0;
            for (int i = 1; i < portate.size(); i++) {
                Portata currentPortata = portate.get(i);
                if (currentPortata.getNome().compareTo(smallestPortata.getNome()) < 0) {
                    smallestPortata = currentPortata;
                    smallestIndex = i;
                }
            }
            sortedPortate.add(smallestPortata);
            portate.remove(smallestIndex);
        }
        return sortedPortate;
    }

}
