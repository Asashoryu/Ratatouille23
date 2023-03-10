package com.rat.ratatouille23.model;

import java.util.ArrayList;

public class Ordinazione {
    Tavolo tavolo;
    ArrayList<Portata> portate;

    public Ordinazione(Tavolo tavolo) {
        setTavolo(tavolo);
        portate = new ArrayList<>();
    }

    public void aggiungiPortata(Portata portata) {
        portate.add(portata);
    }

    public void rimuoviPortata(Portata portata) {
        portate.remove(portata);
    }

    public Tavolo getTavolo() {
        return tavolo;
    }

    public void setTavolo(Tavolo tavolo) {
        this.tavolo = tavolo;
    }

    public ArrayList<Portata> getPortate() {
        return portate;
    }

    public void setPortate(ArrayList<Portata> portate) {
        this.portate = portate;
    }

    public Float getCostoTotalePortate() {
        Float costoTotaleConto = 0f;
        for (Portata portata : portate) {
            costoTotaleConto+= portata.getCosto();
        }
        return costoTotaleConto;
    }
}
