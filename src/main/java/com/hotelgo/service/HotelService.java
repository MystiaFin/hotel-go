package com.hotelgo.service;

import com.hotelgo.model.Hotel;
import com.hotelgo.model.HotelRoom;
import com.hotelgo.repository.HotelRepository;

import java.util.List;

public class HotelService {
    private final HotelRepository hotelRepo = new HotelRepository();

    public List<Hotel> findAllHotels() {
        return hotelRepo.findAllHotels();
    }

    public List<HotelRoom> findRoomsByHotelId(long hotelId) {
        return hotelRepo.findRoomsByHotelId(hotelId);
    }

    public Hotel findHotelById(long hotelId) {
        return hotelRepo.getHotelById(hotelId);
    }
}
