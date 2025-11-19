package com.hotelgo.controller;

import java.util.List;

import com.hotelgo.model.BookedHistory;
import com.hotelgo.service.BookingService;
import spark.Request;
import spark.Response;

public class BookingController {
    private static final BookingService bookingService = new BookingService();

    public Object createBooking(Request req, Response res) {
        long userId = Long.parseLong(req.queryParams("userId"));
        long roomId = Long.parseLong(req.queryParams("roomId"));

        boolean success = bookingService.createBooking(userId, roomId);

        res.type("application/json");

        if (success) {
            res.status(201);
            return "{\"message\": \"The room has been booked successfully, please pay and confirm with the receptionist for the room you have chosen within a maximum of 1 hour\"}";
        } else {
            res.status(400);
            return "{\"message\": \"Failed to book a room\"}";
        }
    }

    public Object getActiveBookings(Request req, Response res) {
        Long userId = Long.parseLong(req.queryParams("userId"));
        List<BookedHistory> bookings = bookingService.getActiveBookingsForUser(userId);
        res.type("application/json");
        res.status(200);
        return bookings;
    }
}
