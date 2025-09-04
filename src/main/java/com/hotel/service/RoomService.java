package com.hotel.service;

import com.hotel.dto.RequestRoom;
import com.hotel.entity.Room;

import java.util.List;

public interface RoomService {

    Room createRoom(RequestRoom requestRoom);
    List<Room> getAllRooms();
    List<Room> getAvailableRoomsByHotel(String hotelRefId);
    boolean roomExists(String hotelRefId, Integer floor, Integer roomNumber);
}
