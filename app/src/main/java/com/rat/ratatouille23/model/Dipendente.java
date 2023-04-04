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

    private String cognome;

    private String username;
    private Ruolo ruolo = Ruolo.NONIMPOSTATO;
    private String password;
    private Boolean isReimpostata;

    private String token;

    public Dipendente(String nome, String cognome, String username, Ruolo ruolo, String password, Boolean isReimpostata, String token) {
        setNome(nome);
        setCognome(cognome);
        setUsername(username);
        setRuolo(ruolo);
        setPassword(password);
        setReimpostata(isReimpostata);
        setToken(token);
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

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
