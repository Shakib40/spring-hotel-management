package com.hotel.repository;

import com.hotel.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, String> {
    boolean existsByHotelRefIdAndFloorAndRoomNumber(String hotelRefId, Integer floor, Integer roomNumber);
    boolean existsByAvailability(boolean availability);
    List<Room> findByHotelRefIdAndAvailabilityTrue(String hotelRefId);
}
