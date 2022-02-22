package com.example.hoteleye.databases.entities;

import java.io.Serializable;

public class ServiceItem implements Serializable {
    private String id;
    private String name;
    private float price;
    private String unity;
    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getUnity() {
        return unity;
    }

    public void setUnity(String unity) {
        this.unity = unity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ServiceItem(String id) {
        this.id = id;
    }

    public ServiceItem() {
    }

    public ServiceItem(String id, String name, float price, String unity, String description) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.unity = unity;
        this.description = description;
    }

    @Override
    public String toString() {
        return "ServiceItem{" +
                "id='" + id + '\'' +
                '}';
    }
}
