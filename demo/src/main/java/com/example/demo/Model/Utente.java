package com.example.demo.Model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="user")
public class Utente {
    @Id
    @Column(name="username")
    private String username;
    @Column(name="nome")
    private String nome;

    @Column(name="cognome")
    private String cognome;

    @Column(name="password")
    private String password;
    @Column(name="role")
    private String role;
    @Column(name="is_reimpostata")
    private boolean is_reimpostata;
    // alt + ins Constructor + select none

    public Utente() {
    }
    // alt + ins Constructor + todos

    public Utente(String username, String nome, String cognome, String password, String role, boolean is_reimpostata) {
        this.username = username;
        this.nome = nome;
        this.cognome = cognome;
        this.password = password;
        this.role = role;
        this.is_reimpostata = is_reimpostata;
    }


    // alt ins + getter setter todos


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
