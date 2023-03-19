package com.example.demo.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="piatto")
public class Dish {
    @Id
    @Column(name="nome")
    private String name;
    @Column(name="prezzo")
    private Float price;
    @Column(name="categoria")
    private String category;
    @Column(name="allergie")
    private String allergy;
    @Column(name="description")
    private String descritpion;

    // uno a molti si crea la lista del molti
    @OneToMany(mappedBy = "dish" ,fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Ordered_Dish> ordered_dishes= new ArrayList<>();

    @OneToMany(mappedBy = "dish",fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Make_Dish> made_dishes = new ArrayList<>();

    public Dish() {
    }

    public Dish(String name, Float price, String category, String allergy, String descritpion,List<Ordered_Dish> ordered_dishes,List<Make_Dish> make_dishes) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.allergy = allergy;
        this.descritpion = descritpion;
        this.ordered_dishes = ordered_dishes;
        this.made_dishes = made_dishes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAllergy() {
        return allergy;
    }

    public void setAllergy(String allergy) {
        this.allergy = allergy;
    }

    public List<Ordered_Dish> getOrdered_dishes() {
        return ordered_dishes;
    }

    public void setOrdered_dishes(List<Ordered_Dish> ordered_dishes) {
        this.ordered_dishes = ordered_dishes;
    }

    public List<Make_Dish> getMade_dishes() {
        return made_dishes;
    }

    public void setMade_dishes(List<Make_Dish> made_dishes) {
        this.made_dishes = made_dishes;
    }

    public String getDescritpion() {
        return descritpion;
    }

    public void setDescritpion(String descritpion) {
        this.descritpion = descritpion;
    }
}
