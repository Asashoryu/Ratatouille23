package com.example.demo.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="conto")
public class Conto {
    @Id
    @Column(name="id")
    private int id;

    @Column(name="time")
    private int time;

    @Column(name="total")
    private float total;

    @Column(name="is_chiuso")
    private boolean is_chiuso;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "numero_tavolo", referencedColumnName = "id")
    @JsonManagedReference
    private Tavolo tavolo;

    @OneToMany(mappedBy = "conto" ,fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Ordered_Dish> ordered_dishes= new ArrayList<>();

    public Conto() {
    }

    public Conto(int id, int time, float total, boolean is_chiuso, Tavolo tavolo,List<Ordered_Dish> ordered_dishes) {
        this.id = id;
        this.time = time;
        this.total = total;
        this.is_chiuso = is_chiuso;
        this.tavolo = tavolo;
        this.ordered_dishes=ordered_dishes;
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

    public List<Ordered_Dish> getOrdered_dishes() {
        return ordered_dishes;
    }

    public void setOrdered_dishes(List<Ordered_Dish> ordered_dishes) {
        this.ordered_dishes = ordered_dishes;
    }
}
