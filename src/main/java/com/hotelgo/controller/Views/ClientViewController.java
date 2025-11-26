package com.hotelgo.controller.Views;

import com.hotelgo.config.ThymeleafConfig;
import com.hotelgo.config.ThymeleafTemplateEngine;
import com.hotelgo.model.HotelRoom;
import com.hotelgo.model.User;
import com.hotelgo.model.BookedHistory;
import com.hotelgo.service.HotelService;
import com.hotelgo.service.RoomService;
import com.hotelgo.service.BookingService;
import com.hotelgo.service.UserService;
import com.hotelgo.util.JwtUtil;
import com.hotelgo.util.NavLinkUtil;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import java.util.HashMap;
import java.util.List;
import static com.hotelgo.util.PopupUtil.addPopupFromSession;

public class ClientViewController {
	private final ThymeleafTemplateEngine engine;
	private final UserService userService;
	private final HotelService hotelService;
	private final RoomService roomService;
	private final BookingService bookingService;

	public ClientViewController() {
		this.engine = ThymeleafConfig.createTemplateEngine();
		this.userService = new UserService();
		this.hotelService = new HotelService();
		this.roomService = new RoomService();
		this.bookingService = new BookingService();
	}

	private User getUserFromSession(Request req) {
        String token = req.session().attribute("token");
        if (token != null && !token.isEmpty()) {
            String username = JwtUtil.getUsername(token);
            return userService.getUserByUsername(username);
        }
        return null;
    }

	private void injectCommonData(Request req, HashMap<String, Object> model) {
        User user = getUserFromSession(req);
        if (user != null) {
            model.put("user", user);
            model.put("navLinks", NavLinkUtil.getNavLinks(user.getRole()));
        }
		addPopupFromSession(req, model);
    }

	public ModelAndView home(Request req, Response res) {
		HashMap<String, Object> model = new HashMap<>();
		injectCommonData(req, model); 
		model.put("hotels", hotelService.findAllHotels());
		model.put("title", "Home");
		model.put("currentPath", req.pathInfo());
		return new ModelAndView(model, "pages/client/home");
	}

	public ModelAndView hotelRooms(Request req, Response res) {
		HashMap<String, Object> model = new HashMap<>();
		injectCommonData(req, model);
		long id = Long.parseLong(req.params("id"));
		model.put("hotelId", id);
		model.put("rooms", hotelService.findRoomsByHotelId(id));
		model.put("title", "Hotel Rooms");
		model.put("currentPath", req.pathInfo());
		return new ModelAndView(model, "pages/client/hotel-rooms");
	}

	public ModelAndView confirmBooking(Request req, Response res) {
		HashMap<String, Object> model = new HashMap<>();
		injectCommonData(req, model);
		long roomId = Long.parseLong(req.params("roomId"));
		HotelRoom room = roomService.getRoomById(roomId);
		model.put("room", room);
		model.put("hotel", hotelService.findHotelById(room.getHotelId()));
		model.put("title", "Confirm Booking");
		model.put("currentPath", req.pathInfo());
		return new ModelAndView(model, "pages/client/booking");
	}

	public ModelAndView activeBookings(Request req, Response res) {
		HashMap<String, Object> model = new HashMap<>();
		injectCommonData(req, model);
        String username = JwtUtil.getUsername(req.session().attribute("token"));
        User user = userService.getUserByUsername(username);
		List<BookedHistory> activeBookings = bookingService.getActiveBookings(user.getId());
		model.put("activeBookings", activeBookings);
		model.put("title", "Active Bookings");
		model.put("currentPath", req.pathInfo());
		return new ModelAndView(model, "pages/client/active-bookings");
	}

	public ModelAndView allBookings(Request req, Response res) {
		HashMap<String, Object> model = new HashMap<>();
		injectCommonData(req, model);
        String username = JwtUtil.getUsername(req.session().attribute("token"));
        User user = userService.getUserByUsername(username);
		List<BookedHistory> allBookings = bookingService.getAllBookings(user.getId());
		model.put("allBookings", allBookings);
		model.put("title", "Bookings History");
		model.put("currentPath", req.pathInfo());
		return new ModelAndView(model, "pages/client/all-bookings");
	}
}