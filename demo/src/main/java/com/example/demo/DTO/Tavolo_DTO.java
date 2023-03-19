package com.example.demo.DTO;

public class Tavolo_DTO {
    private int id;
    private boolean taken;

    public Tavolo_DTO() {
    }

    public Tavolo_DTO(int id, boolean taken) {
        this.id = id;
        this.taken = taken;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isTaken() {
        return taken;
    }

    public void setTaken(boolean taken) {
        this.taken = taken;
    }
}
