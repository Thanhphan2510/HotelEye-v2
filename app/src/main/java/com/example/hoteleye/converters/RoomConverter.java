package com.example.hoteleye.converters;

import com.example.hoteleye.databases.entities.Room;
import com.example.hoteleye.viewmodels.RoomNameItem;

import java.util.ArrayList;
import java.util.List;

public class RoomConverter {
    public static RoomNameItem covertRoomToRoomNameItem(Room room) {
        if (room == null) {
            return null;
        }
        RoomNameItem result = new RoomNameItem();
        result.setRoomID(String.valueOf(room.getId()));
        result.setName(String.valueOf(room.getRoom_number()));
        result.setRoom_status(room.getStatus());
        return result;
    }
    public static List<RoomNameItem> covertRoomsToRoomNameItems(List<Room> rooms){
        if(rooms == null){
            return null;
        }
        List<RoomNameItem> result = new ArrayList<>();
        for(Room r: rooms){
            result.add(covertRoomToRoomNameItem(r));
        }
        return result;
    }
}
