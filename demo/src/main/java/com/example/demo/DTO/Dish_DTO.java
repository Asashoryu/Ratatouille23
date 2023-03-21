package com.example.demo.DTO;

public class Dish_DTO {
    private String name;
    private float price;
    private String category;
    private String allergy;

    private  boolean ordinabile;
    private String description;

    public Dish_DTO() {
    }

    public Dish_DTO(String name, float price, String category, String allergy, boolean ordinabile, String description) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.allergy = allergy;
        this.ordinabile = ordinabile;
        this.description = description;
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

    public boolean isOrdinabile() {
        return ordinabile;
    }

    public void setOrdinabile(boolean ordinabile) {
        this.ordinabile = ordinabile;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
