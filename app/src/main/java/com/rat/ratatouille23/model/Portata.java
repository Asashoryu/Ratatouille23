package com.rat.ratatouille23.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Portata {
    private String nome;
    private Float costo;
    private String descrizione;
    private List<Allergene> allergeni;

    public Portata(String nome, Float costo, String descrizione, ArrayList<Allergene> allergeni) {
        setNome(nome);
        setCosto(costo);
        setDescrizione(descrizione);
        allergeni = new ArrayList<>();
        setAllergeni(allergeni);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Float getCosto() {
        return costo;
    }

    public void setCosto(Float costo) {
        this.costo = costo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public List<Allergene> getAllergeni() {
        return allergeni;
    }

    public void setAllergeni(ArrayList<Allergene> allergeni) {
        this.allergeni = allergeni;
    }
}
