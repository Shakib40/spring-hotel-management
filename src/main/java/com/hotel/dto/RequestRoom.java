package com.hotel.dto;
import com.hotel.entity.Room;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class RequestRoom {
    private String hotelRefId;
    private Integer floor;
    private Integer roomNumber;
    private Boolean availability = true;
    private Room.RoomType roomType;
    private BigDecimal pricePerDay;
}
