package com.hotelgo.controller;

import com.hotelgo.service.HotelService;
import spark.Request;
import spark.Response;

public class HotelController {
    private static final HotelService hotelService = new HotelService();

    public Object getHotels(Request req, Response res) {
        return hotelService.findAllHotels();
    }

    public Object getRoomsByHotelId(Request req, Response res) {
        long hotelId = Long.parseLong(req.params("id"));
        return hotelService.findRoomsByHotelId(hotelId);
    }
}
