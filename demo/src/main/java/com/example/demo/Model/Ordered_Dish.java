package com.example.demo.Model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name="ordered_dish")

public class Ordered_Dish {
    @Id
    @Column(name="corrispondenza")
    private int id;
    @Column(name="quantita")
    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "conto_id", referencedColumnName = "id")
    @JsonManagedReference
    private Conto conto;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "nome_piatto", referencedColumnName = "nome")
    @JsonManagedReference
    private Dish dish;

    public Ordered_Dish() {
    }

    public Ordered_Dish(int id, int quantity,Conto conto,Dish dish) {
        this.id = id;
        this.quantity = quantity;
        this.conto = conto;
        this.dish = dish;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Conto getConto() {
        return conto;
    }

    public void setConto(Conto conto) {
        this.conto = conto;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }
}
