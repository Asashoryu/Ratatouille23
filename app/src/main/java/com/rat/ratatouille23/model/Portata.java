package com.rat.ratatouille23.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Portata {
    private String nome;
    private Float costo;
    private String descrizione;
    private ArrayList<Allergene> allergeni;

    private ArrayList<Ingrediente> ingredienti;

    public Portata(String nome, Float costo, String descrizione, ArrayList<Allergene> allergeni) {
        setNome(nome);
        setCosto(costo);
        setDescrizione(descrizione);
        allergeni = new ArrayList<>();
        setAllergeni(allergeni);
        ingredienti = new ArrayList<Ingrediente>();
    }

    public void aggiungiIngrediente(Ingrediente ingrediente) {
        ingredienti.add(ingrediente);
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

    public ArrayList<Ingrediente> getIngredienti() {
        return ingredienti;
    }

    public void setIngredienti(ArrayList<Ingrediente> ingredienti) {
        this.ingredienti = ingredienti;
    }
}
