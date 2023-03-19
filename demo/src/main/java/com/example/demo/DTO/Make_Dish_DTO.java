package com.example.demo.DTO;

import com.example.demo.Model.Dish;
import com.example.demo.Model.Ingridient;

public class Make_Dish_DTO {
    private int id;
    private float quantity;
    private Dish dish;
    private Ingridient ingridient;


    public Make_Dish_DTO() {
    }

    public Make_Dish_DTO(int id, float quantity,  Dish dish, Ingridient ingridient) {
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
