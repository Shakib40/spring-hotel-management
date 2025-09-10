package com.hotel.repository;

import com.hotel.entity.Hotel;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel, String> {
    boolean existsByName(String name);

//    boolean existsByUserRefId(String userRefId);
    boolean existsByUserRefId(@NotBlank(message = "User reference ID is required") String userRefId);
}
