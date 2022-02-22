package com.example.hoteleye.databases.entities;

public class RoomType {
    private int roomtype_id;
    private String type_name;
    private int capacity;

    private String description;

    public RoomType(int roomtype_id, String type_name, int capacity, String description) {
        this.roomtype_id = roomtype_id;
        this.type_name = type_name;
        this.capacity = capacity;
        this.description = description;
    }
}
