package com.rat.ratatouille23.model;

public class PortataOrdine {

    private Portata portata;

    private Ordinazione ordinazione;

    private Integer quantita;

    private Float costoTotalePortataOrdine;

    public PortataOrdine(Ordinazione ordinazione, Portata portata) {
        this.ordinazione = ordinazione;
        this.portata = portata;
        this.quantita = 1;
        aggiornaCostoPortataOrdine();
    }

    public PortataOrdine(Ordinazione ordinazione, Portata portata, Integer quantita) {
        this.ordinazione = ordinazione;
        this.portata = portata;
        this.quantita = quantita;
        aggiornaCostoPortataOrdine();
    }

    public void incrementaQuantita() {
        quantita++;
        aggiornaCostoPortataOrdine();
    }

    public void decrementaQuantita() {
        if (quantita > 0) {
            quantita--;
            aggiornaCostoPortataOrdine();
        }
        else {
            ordinazione.rimuoviPortataOrdine(this);
        }
    }

    public void aggiornaCostoPortataOrdine() {
        costoTotalePortataOrdine = portata.getCosto() * quantita;
    }

    public Portata getPortata() {
        return portata;
    }

    public void setPortata(Portata portata) {
        this.portata = portata;
    }

    public Integer getQuantita() {
        return quantita;
    }

    public void setQuantita(Integer quantita) {
        this.quantita = quantita;
    }

    public Ordinazione getOrdinazione() {
        return ordinazione;
    }

    public void setOrdinazione(Ordinazione ordinazione) {
        this.ordinazione = ordinazione;
    }

    public Float getCostoTotalePortataOrdine() {
        return costoTotalePortataOrdine;
    }

    public void setCostoTotalePortataOrdine(Float costoTotalePortataOrdine) {
        this.costoTotalePortataOrdine = costoTotalePortataOrdine;
    }
}
