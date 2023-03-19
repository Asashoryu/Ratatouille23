package com.example.demo.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="tavolo")
public class Tavolo {
    @Id
    @Column(name="id")
    private int id;
    @Column(name="taken")
    private boolean taken;

    @OneToMany(mappedBy = "tavolo" ,fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Conto> conti= new ArrayList<>();


    public Tavolo() {

    }
    public Tavolo(int id, boolean taken,List<Conto> conti) {
        this.id = id;
        this.taken = taken;
        this.conti=conti;
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

    public void setTaken(boolean taken) {this.taken = taken;}

    public List<Conto> getConti() {
        return conti;
    }

    public void setConti(List<Conto> conti) {
        this.conti = conti;
    }
}


