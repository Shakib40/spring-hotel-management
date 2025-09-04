package com.hotel.dto;

import com.hotel.entity.HotelLocation;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HotelResponse {
    private String id;
    private String userRefId;
    private String name;
    private String description;
    private HotelLocation location;
}
