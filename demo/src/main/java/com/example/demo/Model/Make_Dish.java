package com.example.demo.Model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name="make_dish")
public class Make_Dish {
    @Id
    @Column(name="corrispondenza")
    private int id;

    @Column(name="quantity")
    private float quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "nome_ingrediente", referencedColumnName = "nome")
    @JsonManagedReference
    private Ingridient ingridient;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "nome_piatto", referencedColumnName = "nome")
    @JsonManagedReference
    private Dish dish;

    public Make_Dish() {
    }

    public Make_Dish(int id, float quantity,Dish dish,Ingridient ingridient) {
        this.id = id;
        this.quantity = quantity;
        this.ingridient = ingridient;
        this.dish = dish;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public Ingridient getIngridient() {
        return ingridient;
    }

    public void setIngridient(Ingridient ingridient) {
        this.ingridient = ingridient;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }
}