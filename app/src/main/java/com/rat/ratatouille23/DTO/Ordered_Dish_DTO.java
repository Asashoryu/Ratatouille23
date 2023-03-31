package com.rat.ratatouille23.DTO;

public class Ordered_Dish_DTO {
    private int id;
    private int quantity;
    private int contoId;
    private String dishName;

    public Ordered_Dish_DTO() {

    }

    public Ordered_Dish_DTO(int quantity, int contoId, String dishName) {
        this.quantity = quantity;
        this.contoId = contoId;
        this.dishName = dishName;
    }

    public Ordered_Dish_DTO(int id, int quantity, int contoId, String dishName) {
        this.id = id;
        this.quantity = quantity;
        this.contoId = contoId;
        this.dishName = dishName;
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

    public int getContoId() {
        return contoId;
    }

    public void setContoId(int contoId) {
        this.contoId = contoId;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }
}