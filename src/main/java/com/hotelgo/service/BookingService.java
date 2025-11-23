package com.hotelgo.service;

import java.util.List;

import com.hotelgo.model.BookedHistory;
import com.hotelgo.repository.BookingRepository;

public class BookingService {
    private final BookingRepository repository = new BookingRepository();

    public String createBooking(long userId, long roomId) {
        if (repository.isRoomBooked(roomId)) {
            return "ERROR: Room already booked";
        }

        boolean success = repository.createBooking(userId, roomId);
        if (success) {
            return "SUCCESS: The room has been booked successfully, please pay and confirm with the receptionist for the room you have chosen within a maximum of 1 hour";
        } else {
            return "ERROR: Failed to book room";
        }
    }

    public List<BookedHistory> getActiveBookingsForUser(Long userId)  {
        return repository.getActiveBookingsByUser(userId);
    }

    public List<BookedHistory> getAllBookings() {
        return repository.findAllBookings();
    }
    
    public void updateBookingStatus(long id, String status) {
        repository.updateStatus(id, status);
    }

    public List<BookedHistory> searchBookings(String query) {
        return repository.searchBookings(query);
    }
}