package com.example.demo.DTO;

import com.example.demo.Model.Conto;
import com.example.demo.Model.Dish;

public class Ordered_Dish_DTO {
    private int id;
    private int quantity;
    private Conto conto;
    private Dish dish;

    public Ordered_Dish_DTO() {
    }

    public Ordered_Dish_DTO(int id, int quantity, Conto conto, Dish dish) {
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
