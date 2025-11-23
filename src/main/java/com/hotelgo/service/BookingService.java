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

    public List<BookedHistory> getActiveBookings(Long userId) {
        return repository.getBookings(userId, "ACTIVE", false);
    }

    public List<BookedHistory> getAllBookings(Long userId) {
        List<BookedHistory> bookings = repository.getBookings(userId, null, false);

        bookings.sort((a, b) -> {
            if (a.getBookedStatus().equals("PENDING") && !b.getBookedStatus().equals("PENDING"))
                return -1;
            if (!a.getBookedStatus().equals("PENDING") && b.getBookedStatus().equals("PENDING"))
                return 1;
            return 0;
        });

        return bookings;
    }

    public String cancelBooking(Long id) {
        boolean success = repository.cancelBooking(id);
        if (success) {
            return "SUCCESS: Your booking has been successfully canceled";
        } else {
            return "ERROR: Failed to cancel booking";
        }
    }
}
