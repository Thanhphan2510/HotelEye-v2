package com.example.hoteleye.viewmodels;

public class FurnitureItem {
    private String name;
    private String unity;
    private float price;
    private String description;

    public FurnitureItem(String name, String unity, float price, String description) {
        this.name = name;
        this.unity = unity;
        this.price = price;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnity() {
        return unity;
    }

    public void setUnity(String unity) {
        this.unity = unity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
