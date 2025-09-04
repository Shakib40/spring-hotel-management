package com.hotel.serviceimp;
import com.hotel.dto.RequestRoom;
import com.hotel.entity.Room;
import com.hotel.repository.RoomRepository;
import com.hotel.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    @Override
    public Room createRoom(RequestRoom requestRoom) {
        // Prevent duplicate room in same hotel & floor
        System.out.println("HELLO Word");
        if (roomRepository.existsByHotelRefIdAndFloorAndRoomNumber(requestRoom.getHotelRefId(), requestRoom.getFloor(), requestRoom.getRoomNumber())) {
//            throw new IllegalArgumentException("Room already exists for this hotel & floor");
             throw  new RuntimeException("Room already exists for this hotel & floor");
        }

        // ✅ Map DTO → Entity
        Room room = new Room();
        room.setHotelRefId(requestRoom.getHotelRefId());
        room.setFloor(requestRoom.getFloor());
        room.setRoomNumber(requestRoom.getRoomNumber());
        room.setAvailability(requestRoom.getAvailability() != null ? requestRoom.getAvailability() : true);
        room.setRoomType(requestRoom.getRoomType());
        room.setPricePerDay(requestRoom.getPricePerDay());

        // ✅ Save entity
        return roomRepository.save(room);
    }


    @Override
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    @Override
    public List<Room> getAvailableRoomsByHotel(String hotelRefId) {
        return roomRepository.findByHotelRefIdAndAvailabilityTrue(hotelRefId);
    }

    @Override
    public boolean roomExists(String hotelRefId, Integer floor, Integer roomNumber) {
        return roomRepository.existsByHotelRefIdAndFloorAndRoomNumber(hotelRefId, floor, roomNumber);
    }
}
