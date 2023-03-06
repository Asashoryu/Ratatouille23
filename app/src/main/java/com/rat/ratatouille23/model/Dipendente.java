package com.rat.ratatouille23.model;

import com.rat.ratatouille23.eccezioni.ReimpostaPasswordException;

public class Dipendente {
    public enum Ruolo{
        NONIMPOSTATO,
        AMMINISTRATORE,
        SUPERVISORE,
        ADDETTOSALA,
        ADDETTOCUCINA
    };

    private String nome;
    private Ruolo ruolo = Ruolo.NONIMPOSTATO;
    private String password;
    private Boolean isReimpostata;

    public Dipendente(String nome, String password, Boolean isReimpostata, Ruolo ruolo) {
        setNome(nome);
        setPassword(password);
        setReimpostata(isReimpostata);
        setRuolo(ruolo);
    }

    public void reimpostaPassword(String nuovaPassword) throws ReimpostaPasswordException {
        // TODO: validare la nuova password rispetto alla vecchia e nel caso di errore lanciare un eccezione
        setPassword(nuovaPassword);
        Boolean erroreValidazione = false;
        if (erroreValidazione) {
            throw new ReimpostaPasswordException();
        }
    }

    public String getNome() {
        return nome;
    }

    private void setNome(String nome) {
        this.nome = nome;
    }

    public Ruolo getRuolo() {
        return ruolo;
    }

    private void setRuolo(Ruolo ruolo) {
        this.ruolo = ruolo;
    }

    public String getPassword() {
        return password;
    }

    private void setPassword(String password) {
        this.password = password;
    }

    public Boolean getReimpostata() {
        return isReimpostata;
    }

    private void setReimpostata(Boolean reimpostata) {
        isReimpostata = reimpostata;
    }
}
