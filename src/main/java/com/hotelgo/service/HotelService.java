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

    public Long createHotel(String name, String location) {
        return hotelRepo.createHotel(name, location);
    }

    public boolean updateHotel(Long id, String name, String location) {
        return hotelRepo.updateHotel(id, name, location);
    }

    public boolean deleteHotel(Long id) {
        return hotelRepo.deleteHotel(id);
    }
}
