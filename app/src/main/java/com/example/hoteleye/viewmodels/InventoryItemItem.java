package com.example.hoteleye.viewmodels;

import com.example.hoteleye.databases.entities.ServiceItem;

public class InventoryItemItem {
    private String id;
    private String name;
    private int quantity;
    private String date;
    private float price;
    private ServiceItem serviceItem = new ServiceItem();
    private String description;

    @Override
    public String toString() {
        return "InventoryItemItem{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", date='" + date + '\'' +
                ", price=" + price +
                ", serviceItem=" + serviceItem +
                ", description='" + description + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public InventoryItemItem(String id, String name, int quantity, String date, float price, ServiceItem serviceItem, String description) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.date = date;
        this.price = price;
        this.serviceItem = serviceItem;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ServiceItem getServiceItem() {
        return serviceItem;
    }

    public void setServiceItem(ServiceItem serviceItem) {
        this.serviceItem = serviceItem;
    }

    //    public InventoryItemItem(String name, int quantity, String date, float price, String description) {
//        this.name = name;
//        this.quantity = quantity;
//        this.date = date;
//        this.price = price;
//        this.description = description;
//    }
}
