package com.rat.ratatouille23.DTO;

import com.google.gson.annotations.SerializedName;

public class Utente_DTO {
    private String username;
    private String nome;
    private String cognome;
    private String password;
    private String ruolo;

    @SerializedName("isReimpostata")
    private Boolean IsReimpostata;

    private String token;

    public Utente_DTO() {
    }

    public Utente_DTO(String username, String nome, String cognome, String password, String ruolo, boolean IsReimpostata) {
        this.username = username;
        this.nome = nome;
        this.cognome = cognome;
        this.password = password;
        this.ruolo = ruolo;
        this.IsReimpostata = IsReimpostata;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRuolo() {
        return ruolo;
    }

    public void setRuolo(String ruolo) {
        this.ruolo = ruolo;
    }

    public Boolean getIsReimpostata() {
        return IsReimpostata;
    }

    public void setIsReimpostata(Boolean IsReimpostata) {
        this.IsReimpostata = IsReimpostata;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

