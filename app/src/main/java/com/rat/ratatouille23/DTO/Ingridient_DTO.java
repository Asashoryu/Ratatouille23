package com.rat.ratatouille23.DTO;

public class Ingridient_DTO {
    private String name;
    private float price;
    private float quantity;
    private String misura;

    private float soglia;
    private float tolleranza;
    private String description;

    public Ingridient_DTO() {
    }

    public Ingridient_DTO(String name, float price, float quantity, String misura, float soglia, float tolleranza, String description) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.misura = misura;
        this.soglia = soglia;
        this.tolleranza = tolleranza;
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

    public float getSoglia() {
        return soglia;
    }

    public void setSoglia(float soglia) {
        this.soglia = soglia;
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
}
