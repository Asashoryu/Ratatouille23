package com.example.demo.DTO;

public class Utente_DTO {
    private String username;
    private String nome;
    private String cognome;
    private String password;
    private String role;
    private boolean is_reimpostata;

    public Utente_DTO() {
    }

    public Utente_DTO(String username, String nome, String cognome, String password, String role, boolean is_reimpostata) {
        this.username = username;
        this.nome = nome;
        this.cognome = cognome;
        this.password = password;
        this.role = role;
        this.is_reimpostata = is_reimpostata;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isIs_reimpostata() {
        return is_reimpostata;
    }

    public void setIs_reimpostata(boolean is_reimpostata) {
        this.is_reimpostata = is_reimpostata;
    }
}
