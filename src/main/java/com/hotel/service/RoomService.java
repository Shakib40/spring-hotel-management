package com.hotel.service;

import com.hotel.dto.RequestRoom;
import com.hotel.dto.RoomBulkUploadResponse;
import com.hotel.entity.Room;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface RoomService {

    Room createRoom(RequestRoom requestRoom);
    List<Room> getAvailableRoomsByHotel(String hotelRefId);
    List<Room> getBookedRoomsByHotel(String hotelRefId);
    RoomBulkUploadResponse uploadRoomsFromExcel(MultipartFile file );

    // 🔹 Template download
    byte[] generateRoomExcelTemplate();
}
