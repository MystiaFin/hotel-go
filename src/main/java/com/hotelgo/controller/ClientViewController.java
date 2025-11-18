package com.hotelgo.controller;

import com.hotelgo.config.ThymeleafConfig;
import com.hotelgo.config.ThymeleafTemplateEngine;
import com.hotelgo.model.HotelRoom;
import com.hotelgo.model.User;
import com.hotelgo.service.HotelService;
import com.hotelgo.service.RoomService;
import com.hotelgo.service.UserService;
import com.hotelgo.util.JwtUtil;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;

public class ClientViewController {
    private final ThymeleafTemplateEngine engine;
    private final UserService userService;
    private final HotelService hotelService;
    private final RoomService roomService;

    public ClientViewController() {
        this.engine = ThymeleafConfig.createTemplateEngine();
        this.userService = new UserService();
        this.hotelService = new HotelService();
        this.roomService = new RoomService();
    }

    public ModelAndView hotelRooms(Request req, Response res) {
        HashMap<String, Object> model = new HashMap<>();
        long id = Long.parseLong(req.params("id"));
        model.put("hotelId", id);
        model.put("rooms", hotelService.findRoomsByHotelId(id));
        return new ModelAndView(model, "pages/client/hotel-rooms");
    }

    public ModelAndView confirmBooking(Request req, Response res) {
        HashMap<String, Object> model = new HashMap<>();
        String username = JwtUtil.getUsername(req.session().attribute("token"));
        User user = userService.getUserByUsername(username);
        model.put("user", user);
        long roomId = Long.parseLong(req.params("roomId"));
        HotelRoom room = roomService.getRoomById(roomId);
        model.put("room", room);
        model.put("hotel", hotelService.findHotelById(room.getHotelId()));
        return new ModelAndView(model, "pages/client/booking");
    }
}
