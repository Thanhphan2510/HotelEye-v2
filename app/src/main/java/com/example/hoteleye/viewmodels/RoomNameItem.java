package com.example.hoteleye.viewmodels;

import java.io.Serializable;

public class RoomNameItem implements Serializable, Comparable<RoomNameItem> {
    private String roomID;
    private String name;
    private int room_status;

    public RoomNameItem(String name, int room_status) {
        this.name = name;
        this.room_status = room_status;
    }

    public RoomNameItem() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRoom_status() {
        return room_status;
    }

    public void setRoom_status(int room_status) {
        this.room_status = room_status;
    }

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    @Override
    public String toString() {
        return "RoomNameItem{" +
                "name='" + name + '\'' +
                ", room_status=" + room_status +
                '}';
    }

    @Override
    public int compareTo(RoomNameItem o) {
        return this.getName().compareTo(o.getName());
    }
}
