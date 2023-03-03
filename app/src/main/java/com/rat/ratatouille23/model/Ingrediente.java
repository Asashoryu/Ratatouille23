package com.rat.ratatouille23.model;

public class Ingrediente {

    private String nome;
    private String quantita;

    public Ingrediente(String nome, String quantita) {
        setNome(nome);
        setQuantita(quantita);
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
}
