package com.rat.ratatouille23.DTO;

public class Make_Dish_DTO {
    private int id;
    private float quantity;
    private String dishName;
    private String ingridientName;

    public Make_Dish_DTO() {
    }

    public Make_Dish_DTO(int id, float quantity, String dishName, String ingridientName) {
        this.id = id;
        this.quantity = quantity;
        this.ingridientName = ingridientName;
        this.dishName = dishName;
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

    public String getIngridientName() {
        return ingridientName;
    }

    public void setIngridientName(String ingridientName) {
        this.ingridientName = ingridientName;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }
}
