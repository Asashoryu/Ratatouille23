package com.rat.ratatouille23.model;

import java.util.ArrayList;
import java.util.List;

public class Portata {
    private String nome;
    private Float costo;
    private String descrizione;

    private String allergeni;
    private ArrayList<IngredientePortata> ingredientiPortata;

    public Portata(String nome, Float costo, String descrizione, String allergeni) {
        setNome(nome);
        setCosto(costo);
        setDescrizione(descrizione);
        setAllergeni(allergeni);
        ingredientiPortata = new ArrayList<IngredientePortata>();
    }

    public void aggiungiIngredientePortata(IngredientePortata ingredientePortata) {
        ingredientiPortata.add(ingredientePortata);
    }

    public void aggiungiIngrediente(Ingrediente ingrediente, Float quantita) {
        IngredientePortata ingredientePortata = new IngredientePortata( this, ingrediente, quantita);
        ingredientiPortata.add(ingredientePortata);
        System.err.println("Aggiunto ingrediente " + ingrediente.getNome() + " alla portata " + nome);
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

    public String getAllergeni() {
        return allergeni;
    }

    public void setAllergeni(String allergeni) {
        this.allergeni = allergeni;
    }

    public ArrayList<IngredientePortata> getIngredientiPortata() {
        return ingredientiPortata;
    }

    public ArrayList<Ingrediente> getIngredienti() {
        ArrayList<Ingrediente> ingredienti  = new ArrayList<>();
        for (IngredientePortata ingredientePortata : ingredientiPortata) {
            ingredienti.add(ingredientePortata.getIngrediente());
        }
        System.out.println("Ecco gli ingredienti trovati del piatto " + nome);
        ingredienti.forEach(ingrediente -> System.out.println(ingrediente.getNome()));
        System.out.println("Fine ingredienti trovati del piatto " + nome);
        return ingredienti;
    }

    public void setIngredienti(ArrayList<IngredientePortata> ingredientiPortata) {
        this.ingredientiPortata = ingredientiPortata;
    }
}
