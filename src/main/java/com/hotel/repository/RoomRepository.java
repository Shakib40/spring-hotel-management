package com.hotel.repository;

import com.hotel.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, String> {

         // 1. Check if room exists for given hotel, floor and room number
         @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END " +
         "FROM Room r " +
         "WHERE r.hotelRefId = :hotelRefId AND r.floor = :floor AND r.roomNumber = :roomNumber")
         boolean existsRoom(@Param("hotelRefId") String hotelRefId,
                   @Param("floor") Integer floor,
                   @Param("roomNumber") Integer roomNumber);

    List<Room> findByHotelRefIdAndAvailabilityTrue(String hotelRefId);
}
