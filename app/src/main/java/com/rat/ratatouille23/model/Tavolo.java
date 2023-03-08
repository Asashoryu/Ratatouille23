package com.rat.ratatouille23.model;

public class Tavolo {
    private String nome;
    private Boolean disponibile;

    public Tavolo(String nome, Boolean disponibile) {
        setNome(nome);
        setDisponibile(disponibile);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Boolean getDisponibile() {
        return disponibile;
    }

    public void setDisponibile(Boolean disponibile) {
        this.disponibile = disponibile;
    }
}
