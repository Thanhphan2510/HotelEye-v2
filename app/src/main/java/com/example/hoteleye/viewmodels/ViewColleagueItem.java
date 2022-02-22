package com.example.hoteleye.viewmodels;

public class ViewColleagueItem {
    private int id;
    private String name;
    private String date;
    private String phone_number;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public ViewColleagueItem(int id, String name, String date, String phone_number) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.phone_number = phone_number;
    }
}
