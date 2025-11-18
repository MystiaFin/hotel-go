package com.hotelgo.controller;

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
            return "{\"message\": \"Kamar berhasil dipesan, silahkan bayar dan konfirmasi ke resepsionis untuk kamar yang anda pilih dalam jangka waktu maksimal 1 jam\"}";
        } else {
            res.status(400);
            return "{\"message\": \"Gagal memesan kamar\"}";
        }
    }
}
