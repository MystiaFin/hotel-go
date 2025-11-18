package com.hotelgo.controller.Views;

import com.hotelgo.config.ThymeleafConfig;
import com.hotelgo.config.ThymeleafTemplateEngine;
import com.hotelgo.model.HotelRoom;
import com.hotelgo.model.User;
import com.hotelgo.model.BookedHistory;
import com.hotelgo.model.SideNavLinks;
import com.hotelgo.service.HotelService;
import com.hotelgo.service.RoomService;
import com.hotelgo.service.BookingService;
import com.hotelgo.service.UserService;
import com.hotelgo.util.JwtUtil;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

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

	private List<SideNavLinks> getClientNavLinks() {
		List<SideNavLinks> links = new ArrayList<>();
		links.add(new SideNavLinks("/", "Home"));
		links.add(new SideNavLinks("/pending", "Pending"));
		links.add(new SideNavLinks("/booking", "Bookings"));
		links.add(new SideNavLinks("/history", "History"));
		return links;
	}

	public ModelAndView home(Request req, Response res) {
		HashMap<String, Object> model = new HashMap<>();
		String token = req.session().attribute("token");

		// Token validation
		if (token == null || token.isEmpty()) {
			res.redirect("/login");
			return null;
		}

		String username = JwtUtil.getUsername(token);
		User user = userService.getUserByUsername(username);

		// User data
		model.put("nama", user.getNama());
		model.put("email", user.getEmail());
		model.put("role", user.getRole());

		// Hotels data
		model.put("hotels", hotelService.findAllHotels());

		// Navigation and metadata
		model.put("title", "Home");
		model.put("currentPath", req.pathInfo());
		model.put("navLinks", getClientNavLinks());

		return new ModelAndView(model, "pages/client/home");
	}

	public ModelAndView hotelRooms(Request req, Response res) {
		HashMap<String, Object> model = new HashMap<>();
		long id = Long.parseLong(req.params("id"));
		model.put("hotelId", id);
		model.put("rooms", hotelService.findRoomsByHotelId(id));
		model.put("currentPath", req.pathInfo());
		model.put("navLinks", getClientNavLinks());
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

		model.put("currentPath", req.pathInfo());
		model.put("navLinks", getClientNavLinks());
		return new ModelAndView(model, "pages/client/booking");
	}

	public ModelAndView activeBookings(Request req, Response res) {
		HashMap<String, Object> model = new HashMap<>();
		String username = JwtUtil.getUsername(req.session().attribute("token"));
		User user = userService.getUserByUsername(username);
		model.put("user", user);
		List<BookedHistory> activeBookings = bookingService.getActiveBookingsForUser(user.getId());
		model.put("activeBookings", activeBookings);
		return new ModelAndView(model, "pages/client/active-bookings");
	}
}
