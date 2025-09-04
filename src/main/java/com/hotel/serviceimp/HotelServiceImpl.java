package com.hotel.serviceimp;

import com.hotel.dto.HotelRequest;
import com.hotel.dto.HotelResponse;
import com.hotel.entity.Hotel;
import com.hotel.entity.HotelLocation;
import com.hotel.repository.HotelRepository;
import com.hotel.service.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;

    @Override
    public HotelResponse createHotel(HotelRequest request) {
        if (hotelRepository.existsByName(request.getName())) {
            throw new RuntimeException("Hotel with this name already exists");
        }

        Hotel hotel = Hotel.builder()
                .userRefId(request.getUserRefId())
                .name(request.getName())
                .description(request.getDescription())
                .location(request.getLocation())
                .build();

        Hotel saved = hotelRepository.save(hotel);
        return toResponse(saved);
    }

    @Override
    public List<HotelResponse> getAllHotels() {
        return hotelRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public HotelResponse getHotelById(String id) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));
        return toResponse(hotel);
    }

    @Override
    public void deleteHotel(String id) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));
        hotelRepository.delete(hotel);
    }

    @Override
    public HotelResponse updateHotel(String id, HotelRequest request) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));

        hotel.setUserRefId(request.getUserRefId());
        hotel.setName(request.getName());
        hotel.setDescription(request.getDescription());
        hotel.setLocation(request.getLocation());

        Hotel updated = hotelRepository.save(hotel);
        return toResponse(updated);
    }

    private HotelResponse toResponse(Hotel hotel) {
        return HotelResponse.builder()
                .id(hotel.getId())
                .userRefId(hotel.getUserRefId())
                .name(hotel.getName())
                .description(hotel.getDescription())
                .location(hotel.getLocation())
                .build();
    }
}
