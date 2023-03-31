package com.rat.ratatouille23.model;

public class Tavolo {
    private Integer id;
    private Boolean disponibile;

    private Ordinazione ordinazione;

    public Tavolo(Integer id, Boolean disponibile) {
        setId(id);
        setDisponibile(disponibile);
        ordinazione = new Ordinazione(this);
    }

    public void occupaTavoloConOrdinazione(Ordinazione ordinazione) {
        setOrdinazione(ordinazione);
        setDisponibile(false);
    }

    public void liberaTavoloDaOrdinazione() {
        setOrdinazione(new Ordinazione(this));
        setDisponibile(true);
    }

    public String getStringId() {
        return id.toString();
    }

    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
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
