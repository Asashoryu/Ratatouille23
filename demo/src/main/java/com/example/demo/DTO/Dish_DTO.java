package com.example.demo.DTO;

public class Dish_DTO {
    private String name;
    private Float price;
    private String category;
    private String allergy;
    private String description;

    public Dish_DTO() {
    }

    public Dish_DTO(String name, Float price, String category, String allergy, String description) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.allergy = allergy;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
