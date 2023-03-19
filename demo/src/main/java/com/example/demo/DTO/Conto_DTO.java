package com.example.demo.DTO;

import com.example.demo.Model.Tavolo;

public class Conto_DTO {
    private int id;
    private int time;
    private float total;
    private boolean is_chiuso;
    private Tavolo tavolo;

    public Conto_DTO() {
    }

    public Conto_DTO(int id, int time, float total, boolean is_chiuso, Tavolo tavolo) {
        this.id = id;
        this.time = time;
        this.total = total;
        this.is_chiuso = is_chiuso;
        this.tavolo = tavolo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public boolean isIs_chiuso() {
        return is_chiuso;
    }

    public void setIs_chiuso(boolean is_chiuso) {
        this.is_chiuso = is_chiuso;
    }

    public Tavolo getTavolo() {
        return tavolo;
    }

    public void setTavolo(Tavolo tavolo) {
        this.tavolo = tavolo;
    }
}
