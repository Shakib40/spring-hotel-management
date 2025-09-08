package com.hotel.controller;

import com.hotel.dto.RequestRoom;
import com.hotel.entity.Room;
import com.hotel.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    // ✅ Create Room & Only ADMIN can Create
    @PostMapping("/create")
    public ResponseEntity<Room> createRoom(@RequestBody RequestRoom requestRoom) {
        return ResponseEntity.ok(roomService.createRoom(requestRoom));
    }


    // ✅ Get available rooms by hotel
    @GetMapping("/available/{hotelRefId}")
    public ResponseEntity<List<Room>> getAvailableRooms(@PathVariable String hotelRefId) {
        return ResponseEntity.ok(roomService.getAvailableRoomsByHotel(hotelRefId));
    }

    // 1. Upload Rooms for Hotel using Excel Sheet, Bulk Upload
    // 2. All Rooms of Hotel, In ASC Order, Like Floor 1, Room 101, 102, 103, Floor 2, Room 101, 102, 103
    // 3.

}
