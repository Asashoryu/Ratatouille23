package com.rat.ratatouille23.model;

public class Tavolo {
    private String nome;
    private Boolean disponibile;

    private Ordinazione ordinazione;

    public Tavolo(String nome, Boolean disponibile) {
        setNome(nome);
        setDisponibile(disponibile);
        ordinazione = new Ordinazione(this);
    }

    public void occupaTavoloConOrdinazione(Ordinazione ordinazione) {
        setOrdinazione(ordinazione);
        setDisponibile(false);
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

    public Ordinazione getOrdinazione() {
        return ordinazione;
    }

    public void setOrdinazione(Ordinazione ordinazione) {
        this.ordinazione = ordinazione;
    }


}
