package com.example.hoteleye.databases.entities;

public class SurCharge {
    private String id;
    private Service service;
    private float unit_price;
    private int quantity;
    private float discount;
    private float total_price;
    private String note;

    public SurCharge(String id, Service service) {
        this.id = id;
        this.service = service;
    }

    @Override
    public String toString() {
        return "SurCharge{" +
                "id='" + id + '\'' +
                ", service=" + service +
                ", unit_price=" + unit_price +
                ", quantity=" + quantity +
                ", discount=" + discount +
                ", total_price=" + total_price +
                ", note='" + note + '\'' +
                '}';
    }

    public SurCharge(String id, Service service, float unit_price, int quantity, float discount, float total_price, String note) {
        this.id = id;
        this.service = service;
        this.unit_price = unit_price;
        this.quantity = quantity;
        this.discount = discount;
        this.total_price = total_price;
        this.note = note;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public float getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(float unit_price) {
        this.unit_price = unit_price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public float getTotal_price() {
        return total_price;
    }

    public void setTotal_price(float total_price) {
        this.total_price = total_price;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
