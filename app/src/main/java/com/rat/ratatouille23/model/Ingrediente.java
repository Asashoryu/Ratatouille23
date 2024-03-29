package com.rat.ratatouille23.model;

public class Ingrediente {

    private String nome;
    private Float quantita;

    private String unitaMisura;

    private Float costo;

    private Float soglia;

    private String descrizione;


    public Ingrediente(String nome, Float costo, Float quantita, String unitaMisura, Float soglia, String descrizione) {
        setNome(nome);
        setCosto(costo);
        setQuantita(quantita);
        setUnitaMisura(unitaMisura);
        setSoglia(soglia);
        setDescrizione(descrizione);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Float getQuantita() {
        return quantita;
    }

    public void setQuantita(Float quantita) {
        this.quantita = quantita;
    }

    public String getUnitaMisura() {
        return unitaMisura;
    }

    public void setUnitaMisura(String unitaMisura) {
        this.unitaMisura = unitaMisura;
    }

    public Float getCosto() {
        return costo;
    }

    public void setCosto(Float costo) {
        this.costo = costo;
    }

    public Float getSoglia() {
        return soglia;
    }

    public void setSoglia(Float soglia) {
        this.soglia = soglia;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }
}
