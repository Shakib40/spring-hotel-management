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

    // ✅ Get all rooms
    @GetMapping("/list")
    public ResponseEntity<List<Room>> getAllRooms() {
        return ResponseEntity.ok(roomService.getAllRooms());
    }

    // ✅ Get available rooms by hotel
    @GetMapping("/available/{hotelRefId}")
    public ResponseEntity<List<Room>> getAvailableRooms(@PathVariable String hotelRefId) {
        return ResponseEntity.ok(roomService.getAvailableRoomsByHotel(hotelRefId));
    }

    // ✅ Check if room exists
    @GetMapping("/exists")
    public ResponseEntity<Boolean> roomExists(
            @RequestParam String hotelRefId,
            @RequestParam Integer floor,
            @RequestParam Integer roomNumber) {
        return ResponseEntity.ok(roomService.roomExists(hotelRefId, floor, roomNumber));
    }
}
