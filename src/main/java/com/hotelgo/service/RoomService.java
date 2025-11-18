package com.hotelgo.service;

import com.hotelgo.model.HotelRoom;
import com.hotelgo.repository.RoomRepository;

public class RoomService {
    private final RoomRepository roomRepository = new RoomRepository();

    public HotelRoom getRoomById(Long id) {
        return roomRepository.findById(id);
    }
}
