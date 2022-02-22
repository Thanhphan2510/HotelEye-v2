package com.example.hoteleye.databases.entities;

import java.util.List;

public class Hotel {
    private int hotel_id;
    private String hotel_name;
    private String company_name, address;
    private int star_level;
    private List<Room> rooms;
    private String description;

    public Hotel(int hotel_id, String hotel_name, String company_name, String address, int star_level, List<Room> rooms, String description) {
        this.hotel_id = hotel_id;
        this.hotel_name = hotel_name;
        this.company_name = company_name;
        this.address = address;
        this.star_level = star_level;
        this.rooms = rooms;
        this.description = description;
    }
}
