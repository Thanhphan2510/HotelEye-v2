package com.example.hoteleye.viewmodels;

public class StaffManagerItem {
    private String name;
    private String username, position;

    public StaffManagerItem(String name, String username, String position) {
        this.name = name;
        this.username = username;
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
