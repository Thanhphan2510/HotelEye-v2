package com.example.hoteleye.databases.entities;

import java.io.Serializable;

public class Customer implements Serializable {
    private String id;
    private int id_card;
    private String customer_name;
    private String dateOfBirth;
    private String phone_number;
    private float discount;
    private String note;

    public Customer(String id, int id_card, String customer_name, String dateOfBirth, String phone_number, float discount, String note) {
        this.id = id;
        this.id_card = id_card;
        this.customer_name = customer_name;
        this.dateOfBirth = dateOfBirth;
        this.phone_number = phone_number;
        this.discount = discount;
        this.note = note;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getId_card() {
        return id_card;
    }

    public void setId_card(int id_card) {
        this.id_card = id_card;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
