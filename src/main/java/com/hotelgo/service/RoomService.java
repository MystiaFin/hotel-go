package com.hotelgo.service;

import java.math.BigDecimal;
import java.util.List;

import com.hotelgo.model.HotelRoom;
import com.hotelgo.repository.RoomRepository;

public class RoomService {
    private final RoomRepository roomRepository = new RoomRepository();

    public HotelRoom getRoomById(Long id) {
        return roomRepository.findById(id);
    }

    public List<HotelRoom> findByHotelId(Long hotelId) {
        return roomRepository.findByHotelId(hotelId);
    }

    public Long createRoom(Long hotelId, String roomNumber, BigDecimal price) {
        return roomRepository.createRoom(hotelId, roomNumber, price);
    }

    public boolean updateRoomPrice(Long id, BigDecimal price) {
        return roomRepository.updateRoomPrice(id, price);
    }

    public boolean deleteRoom(Long id) {
        return roomRepository.deleteRoom(id);
    }
}
