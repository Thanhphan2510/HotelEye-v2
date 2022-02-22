package com.example.hoteleye.viewmodels;

import java.util.List;

public class DetailRoomTypeItem {
    private String room_name;
    private List<String> room_type;

    public DetailRoomTypeItem(String room_name, List<String> room_type) {
        this.room_name = room_name;
        this.room_type = room_type;
    }

    public String getRoom_name() {
        return room_name;
    }

    public void setRoom_name(String room_name) {
        this.room_name = room_name;
    }

    public List getRoom_type() {
        return room_type;
    }

    public void setRoom_type(List room_type) {
        this.room_type = room_type;
    }
}
