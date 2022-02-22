package com.example.hoteleye.databases.entities;

public class Room {
    private long id;
    private int room_number;
    private RoomType roomType;
    private int floor_number;
    private float open_price;
    private float hour_price;
    private float night_price;
    private  String description; //vd: đối diện mặt đường,...
    private int status; // -3: phòng không còn sử dụng, -2: phòng có khách, -1: phòng có người đặt, 0: phòng chưa dọn, 1: phòng đã dọn (phòng trống)
    private String note; //vd: có vấn đề như thiếu chăn lúc khách ra

    public Room(int room_number, RoomType roomType, int floor_number, float open_price, float hour_price, float night_price, String description, int status, String note) {
        this.room_number = room_number;
        this.roomType = roomType;
        this.floor_number = floor_number;
        this.open_price = open_price;
        this.hour_price = hour_price;
        this.night_price = night_price;
        this.description = description;
        this.status = status;
        this.note = note;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getRoom_number() {
        return room_number;
    }

    public void setRoom_number(int room_number) {
        this.room_number = room_number;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public int getFloor_number() {
        return floor_number;
    }

    public void setFloor_number(int floor_number) {
        this.floor_number = floor_number;
    }

    public float getOpen_price() {
        return open_price;
    }

    public void setOpen_price(float open_price) {
        this.open_price = open_price;
    }

    public float getHour_price() {
        return hour_price;
    }

    public void setHour_price(float hour_price) {
        this.hour_price = hour_price;
    }

    public float getNight_price() {
        return night_price;
    }

    public void setNight_price(float night_price) {
        this.night_price = night_price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
