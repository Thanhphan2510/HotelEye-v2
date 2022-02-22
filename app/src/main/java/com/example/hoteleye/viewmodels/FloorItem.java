package com.example.hoteleye.viewmodels;

//use in RoomsOverviewSettingsAdapter
public class FloorItem {
    private String floor_name, rooms_quanitity;

    public FloorItem(String floor_name, String rooms_quanitity) {
        this.floor_name = floor_name;
        this.rooms_quanitity = rooms_quanitity;
    }

    public String getFloor_name() {
        return floor_name;
    }

    public void setFloor_name(String floor_name) {
        this.floor_name = floor_name;
    }

    public String getRooms_quanitity() {
        return rooms_quanitity;
    }

    public void setRooms_quanitity(String rooms_quanitity) {
        this.rooms_quanitity = rooms_quanitity;
    }
}
