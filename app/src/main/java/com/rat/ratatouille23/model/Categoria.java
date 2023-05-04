package com.rat.ratatouille23.model;

import java.util.ArrayList;

public class Categoria {
    private String nome;
    ArrayList<Portata> portate;

    public Categoria(String nome) {
        setNome(nome);
        portate = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public ArrayList<Portata> getPortate() {
        return portate;
    }

    public void setPortate(ArrayList<Portata> portate) {
        for (Portata portata: portate
             ) {
            System.err.println("setPortate : " + portata.getNome());

        }
        this.portate = portate;
    }

}
