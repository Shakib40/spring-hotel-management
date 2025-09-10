package com.hotel.controller;

import com.hotel.dto.RequestRoom;
import com.hotel.dto.RoomBulkUploadResponse;
import com.hotel.entity.Room;
import com.hotel.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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


    @PostMapping("/bulk-upload-room")
    public ResponseEntity<RoomBulkUploadResponse> bulkUploadRoom(@RequestParam("file") MultipartFile file) {
        RoomBulkUploadResponse response = roomService.uploadRoomsFromExcel(file);
        return ResponseEntity.ok(response);
    }

    // 🔹 Download Excel Template
    @GetMapping("/bulk-upload-template")
    public ResponseEntity<byte[]> downloadRoomTemplate() {
        byte[] file = roomService.generateRoomExcelTemplate();

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=RoomsTemplate.xlsx")
                .body(file);
    }



    // 2.  All Rooms of Hotel, In ASC Order, Like Floor 1, Room 101, 102, 103, Floor 2, Room 101, 102, 103

    // ✅ Get available rooms by hotel
    @GetMapping("/available/{hotelRefId}")
    public ResponseEntity<List<Room>> getAvailableRooms(@PathVariable String hotelRefId) {
        return ResponseEntity.ok(roomService.getAvailableRoomsByHotel(hotelRefId));
    }

    // ✅ Get available rooms by hotel
    @GetMapping("/room-booked-list/{hotelRefId}")
    public ResponseEntity<List<Room>> getBookedRoomsByHotel(@PathVariable String hotelRefId) {
        return ResponseEntity.ok(roomService.getBookedRoomsByHotel(hotelRefId));
    }

    // 3. update room availability

}
