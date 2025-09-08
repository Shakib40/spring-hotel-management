package com.hotel.service;

import com.hotel.dto.RequestRoom;
import com.hotel.entity.Room;

import java.util.List;

public interface RoomService {

    Room createRoom(RequestRoom requestRoom);
    List<Room> getAvailableRoomsByHotel(String hotelRefId);
}
