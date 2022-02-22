package com.example.hoteleye.viewmodels;

public class CustomerManagerItem {
    private String customerID;
    private int idCard;
    private String name, date, phone_number;
    private float discount;
    private String note;

    public CustomerManagerItem(String customerID, int idCard, String name, String date, String phone_number, float discount, String note) {
        this.customerID = customerID;
        this.idCard = idCard;
        this.name = name;
        this.date = date;
        this.phone_number = phone_number;
        this.discount = discount;
        this.note = note;
    }

    public CustomerManagerItem(int idCard, String name, String date, String phone_number, float discount, String note) {
        this.idCard = idCard;
        this.name = name;
        this.date = date;
        this.phone_number = phone_number;
        this.discount = discount;
        this.note = note;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public int getIdCard() {
        return idCard;
    }

    public void setIdCard(int idCard) {
        this.idCard = idCard;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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
