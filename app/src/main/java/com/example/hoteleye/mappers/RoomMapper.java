package com.example.hoteleye.mappers;

import com.example.hoteleye.commons.ModelMapper;
import com.example.hoteleye.databases.entities.Room;
import com.google.gson.JsonObject;

public class RoomMapper {
    public String mapRoomRequest(Room fromModel, JsonObject refModel){
        ModelMapper mapper= new ModelMapper();
//        mapper.mapFieldName("room_number",String.valueOf(fromModel.getRoom_number()),);
        return "";
    }

}
