package com.hotelgo.service;

import java.util.List;

import com.hotelgo.model.BookedHistory;
import com.hotelgo.repository.BookingRepository;

public class BookingService {
    private final BookingRepository repository = new BookingRepository();

    public boolean createBooking(long userId, long roomId) {
        return repository.createBooking(userId, roomId);
    }

    public List<BookedHistory> getActiveBookingsForUser(Long userId)  {
        return repository.getActiveBookingsByUser(userId);
    }
}
