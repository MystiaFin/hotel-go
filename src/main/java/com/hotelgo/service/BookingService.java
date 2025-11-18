package com.hotelgo.service;

import com.hotelgo.repository.BookingRepository;

public class BookingService {
    private final BookingRepository repository = new BookingRepository();

    public boolean createBooking(long userId, long roomId) {
        return repository.createBooking(userId, roomId);
    }
}
