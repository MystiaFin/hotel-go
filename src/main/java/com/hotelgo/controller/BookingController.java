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
        String msg = bookingService.createBooking(userId, roomId);
        if (msg.startsWith("SUCCESS")) {
            req.session().attribute("popupMessage", msg.substring(8));
            req.session().attribute("popupType", "success");
            req.session().attribute("redirectUrl", "/");
        } else {
            req.session().attribute("popupMessage", msg.substring(7));
            req.session().attribute("popupType", "error");
            req.session().attribute("redirectUrl", "/booking/confirm/" + roomId);
        }
        res.redirect(req.session().attribute("redirectUrl"));
        return null;
    }

    public Object getActiveBookings(Request req, Response res) {
        Long userId = Long.parseLong(req.queryParams("userId"));
        List<BookedHistory> bookings = bookingService.getActiveBookingsForUser(userId);
        res.type("application/json");
        res.status(200);
        return bookings;
    }
}
