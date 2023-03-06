package com.rat.ratatouille23.model;

public class Ingrediente {

    private String nome;
    private String quantita;

    private String unitaMisura;

    private String costo;

    private String descrizione;

    public Ingrediente(String nome, String quantita) {
        setNome(nome);
        setQuantita(quantita);
    }

    public Ingrediente(String nome, String costo, String quantita, String unitaMisura, String descrizione) {
        setNome(nome);
        setCosto(costo);
        setQuantita(quantita);
        setUnitaMisura(unitaMisura);
        setDescrizione(descrizione);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getQuantita() {
        return quantita;
    }

    public void setQuantita(String quantita) {
        this.quantita = quantita;
    }

    public String getUnitaMisura() {
        return unitaMisura;
    }

    public void setUnitaMisura(String unitaMisura) {
        this.unitaMisura = unitaMisura;
    }

    public String getCosto() {
        return costo;
    }

    public void setCosto(String costo) {
        this.costo = costo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }
}
