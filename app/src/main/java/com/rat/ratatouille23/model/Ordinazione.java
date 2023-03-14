package com.rat.ratatouille23.model;

import java.util.ArrayList;

public class Ordinazione {
    Tavolo tavolo;
    ArrayList<Portata> portate;

    Float costoTotalePortate;

    Boolean isChiusa;

    String minutaggioChiusuraConto;

    public Ordinazione(Tavolo tavolo) {
        setTavolo(tavolo);
        portate = new ArrayList<>();
        costoTotalePortate = 0.0f;
        isChiusa = false;
    }

    public void aggiungiPortata(Portata portata) {
        portate.add(portata);
        costoTotalePortate += portata.getCosto();
    }

    public void aggiornaCostoTotalePortate() {
        Float costoTotalePortate = 0f;
        for (Portata portata : portate) {
            costoTotalePortate+= portata.getCosto();
        }
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

    public void setCostoTotalePortate(Float costoTotalePortate) {
        this.costoTotalePortate = costoTotalePortate;
    }

    public Float getCostoTotalePortate() {
        return costoTotalePortate;
    }

    public Boolean getChiusa() {
        return isChiusa;
    }

    public void setChiusa(Boolean chiusa) {
        isChiusa = chiusa;
    }

    public String getMinutaggioChiusuraConto() {
        return minutaggioChiusuraConto;
    }

    public void setMinutaggioChiusuraConto(String minutaggioChiusuraConto) {
        this.minutaggioChiusuraConto = minutaggioChiusuraConto;
    }
}
