package com.rat.ratatouille23.model;

public class IngredientePortata {
    private Portata portata;
    private Ingrediente ingrediente;
    private Float quantita;

    public IngredientePortata(Portata portata, Ingrediente ingrediente, Float quantita) {
        this.portata = portata;
        this.ingrediente = ingrediente;
        this.quantita = quantita;
    }

    public Portata getPortata() {
        return portata;
    }

    public void setPortata(Portata portata) {
        this.portata = portata;
    }

    public Ingrediente getIngrediente() {
        return ingrediente;
    }

    public void setIngrediente(Ingrediente ingrediente) {
        this.ingrediente = ingrediente;
    }

    public Float getQuantita() {
        return quantita;
    }

    public void setQuantita(Float quantita) {
        this.quantita = quantita;
    }
}
