package com.example.demo.Model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="ingrediente")

public class Ingridient {
    @Id
    @Column(name="nome")
    private String name;
    @Column(name="costo")
    private float price;
    @Column (name="quantita")
    private float quantity;
    @Column (name="misura")
    private  String misura;
    @Column (name="tolleranza")
    private float tolleranza;
    @Column (name="description")
    private String description;





    @OneToMany(mappedBy = "ingridient",fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Make_Dish> make_dishes = new ArrayList<>();

    public Ingridient() {

    }

    public Ingridient(String name, float price, float quantity, String misura, float tolleranza, String description,List<Make_Dish> make_dishes) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.misura = misura;
        this.tolleranza = tolleranza;
        this.description = description;
        this.make_dishes = make_dishes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public String getMisura() {
        return misura;
    }

    public void setMisura(String misura) {
        this.misura = misura;
    }

    public float getTolleranza() {
        return tolleranza;
    }

    public void setTolleranza(float tolleranza) {
        this.tolleranza = tolleranza;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Make_Dish> getMake_dishes() {
        return make_dishes;
    }

    public void setMake_dishes(List<Make_Dish> make_dishes) {
        this.make_dishes = make_dishes;
    }
}
