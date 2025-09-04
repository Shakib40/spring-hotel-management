package com.hotel.service;

import com.hotel.dto.HotelRequest;
import com.hotel.dto.HotelResponse;

import java.util.List;

public interface HotelService {

    HotelResponse createHotel(HotelRequest request);

    List<HotelResponse> getAllHotels();

    HotelResponse getHotelById(String id);

    void deleteHotel(String id);

    HotelResponse updateHotel(String id, HotelRequest request);
}
