package com.example.hoteleye.viewmodels;

public class ServiceItem {
    private String name;
    private float unit_price;
    private String unity, type, description;

    public ServiceItem(String name, float unit_price, String unity, String type, String description) {
        this.name = name;
        this.unit_price = unit_price;
        this.unity = unity;
        this.type = type;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(float unit_price) {
        this.unit_price = unit_price;
    }

    public String getUnity() {
        return unity;
    }

    public void setUnity(String unity) {
        this.unity = unity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
