package com.rat.ratatouille23.model;

import java.util.ArrayList;

public class Ordinazione {

    private int id = -1;
    Tavolo tavolo;
    ArrayList<PortataOrdine> portateOrdine;

    Float costoTotalePortateOrdine;

    Boolean isChiusa;

    String minutaggioChiusuraConto;

    public Ordinazione(Tavolo tavolo) {
        setTavolo(tavolo);
        portateOrdine = new ArrayList<>();
        costoTotalePortateOrdine = 0.0f;
        isChiusa = false;
    }

    public Ordinazione(int id, Float costoTotalePortateOrdine, Boolean isChiusa, String minutaggioChiusuraConto, Tavolo tavolo) {
        this.id = id;
        this.costoTotalePortateOrdine =costoTotalePortateOrdine;
        this.isChiusa = isChiusa;
        this.minutaggioChiusuraConto = minutaggioChiusuraConto;
        setTavolo(tavolo);
        portateOrdine = new ArrayList<>();
        costoTotalePortateOrdine = 0.0f;
        isChiusa = false;
        tavolo.occupaTavoloConOrdinazione(this);
    }

    public Ordinazione(int id, Float costoTotalePortateOrdine, Boolean isChiusa, String minutaggioChiusuraConto) {
        this.id = id;
        this.costoTotalePortateOrdine =costoTotalePortateOrdine;
        this.isChiusa = isChiusa;
        this.minutaggioChiusuraConto = minutaggioChiusuraConto;
        portateOrdine = new ArrayList<>();
        costoTotalePortateOrdine = 0.0f;
        isChiusa = false;
    }

    public void incrementaPortata(Portata portata) {

        boolean trovato = false;

        // Check if the product already exists in an existing PortataOrdine object
        for (PortataOrdine portataOrdine : portateOrdine) {
            if (portataOrdine.getPortata().equals(portata)) {
                // If the product exists, increment the quantity of the existing PortataOrdine object
                portataOrdine.incrementaQuantita();
                trovato = true;
            }
        }

        if (trovato == false) {
            // If the product does not exist, create a new PortataOrdine object and add it to the list
            PortataOrdine newPortataOrdine = new PortataOrdine(this, portata);
            portateOrdine.add(newPortataOrdine);
        }

        aggiornaCostoTotalePortateOrdine();
    }

    public void aggiornaCostoTotalePortateOrdine() {
        // Iterate through the list of PortataOrdine objects and sum up the costs
        float totalCost = 0;
        for (PortataOrdine portataOrdine : portateOrdine) {
            totalCost += portataOrdine.getQuantita() * portataOrdine.getPortata().getCosto();
        }

        // Update the costoTotalePortateOrdine attribute
        costoTotalePortateOrdine = totalCost;
    }


    public void decrementaPortataOrdine(PortataOrdine portataOrdine) {
        // Iterate through the list of PortataOrdine objects
        for (int i = 0; i < portateOrdine.size(); i++) {
            PortataOrdine currentPortataOrdine = portateOrdine.get(i);

            // Check if the current PortataOrdine object is equal to the given object
            if (currentPortataOrdine.equals(portataOrdine)) {
                // If the quantity of the PortataOrdine object is 1, remove it from the list
                if (currentPortataOrdine.getQuantita() == 1) {
                    portateOrdine.remove(i);
                } else {
                    // Otherwise, decrement the quantity by 1
                    currentPortataOrdine.decrementaQuantita();
                }
                return;
            }
        }

        // If the PortataOrdine object is not found, throw an exception
        throw new IllegalArgumentException("PortataOrdine not found in the order.");
    }


    public void decrementaPortata(Portata portata) {
        // Iterate through the list of PortataOrdine objects
        for (int i = 0; i < portateOrdine.size(); i++) {
            PortataOrdine portataOrdine = portateOrdine.get(i);

            // Check if the current PortataOrdine object contains the given product
            if (portataOrdine.getPortata().equals(portata)) {
                // If the quantity of the PortataOrdine object is 1, remove it from the list
                if (portataOrdine.getQuantita() == 1) {
                    portateOrdine.remove(i);
                } else {
                    // Otherwise, decrement the quantity by 1
                    portataOrdine.decrementaQuantita();
                }
                aggiornaCostoTotalePortateOrdine();
                return;
            }
        }

        // If the product is not found, throw an exception
        throw new IllegalArgumentException("Product not found in the order.");
    }

    public void aggiungiPortataOrdine(PortataOrdine portataOrdine) {
        portateOrdine.add(portataOrdine);

        aggiornaCostoTotalePortateOrdine();
    }

    public void rimuoviPortataOrdine(PortataOrdine portataOrdine) {
        portateOrdine.remove(portataOrdine);

        aggiornaCostoTotalePortateOrdine();
    }

    public Tavolo getTavolo() {
        return tavolo;
    }

    public void setTavolo(Tavolo tavolo) {
        this.tavolo = tavolo;
    }

    public ArrayList<PortataOrdine> getPortateOrdine() {
        return portateOrdine;
    }

    public void setPortateOrdine(ArrayList<PortataOrdine> portateOrdine) {
        this.portateOrdine = portateOrdine;
    }

    public void setCostoTotalePortateOrdine(Float costoTotalePortateOrdine) {
        this.costoTotalePortateOrdine = costoTotalePortateOrdine;
    }

    public Float getCostoTotalePortateOrdine() {
        return costoTotalePortateOrdine;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
