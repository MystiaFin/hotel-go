package com.hotelgo.controller.Views;

import static com.hotelgo.util.PopupUtil.addPopupFromSession;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.hotelgo.config.ThymeleafTemplateEngine;
import com.hotelgo.model.Hotel;
import com.hotelgo.model.HotelRoom;
import com.hotelgo.model.SideNavLinks;
import com.hotelgo.model.User;
import com.hotelgo.service.HotelService;
import com.hotelgo.service.RoomService;
import com.hotelgo.service.UserService;
import com.hotelgo.util.JwtUtil;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class AdminViewController {

    private final HotelService hotelService;
    private final RoomService roomService;
    private final UserService userService;

	public AdminViewController() {
        this.hotelService = new HotelService();
        this.roomService = new RoomService();
        this.userService = new UserService();
    }

	private List<SideNavLinks> getAdminNavLinks() {
		List<SideNavLinks> links = new ArrayList<>();
		links.add(new SideNavLinks("/admin/dashboard", "Dashboard Admin"));
		links.add(new SideNavLinks("/admin/hotels", "Hotel Management"));
		return links;
	}

	public ModelAndView adminDashboard(Request req, Response res) {
		List<Hotel> hotels = hotelService.findAllHotels();
		HashMap<String, Object> model = new HashMap<>();
		injectAdminData(req, model);
		model.put("title", "Admin Dashboard");
		model.put("currentpath", req.pathInfo());
		model.put("navlinks", getAdminNavLinks());
		model.put("hotels", hotels);

		return new ModelAndView(model, "pages/admin/dashboard");
	}

	private void injectAdminData(Request req, HashMap<String, Object> model) {
        String token = req.session().attribute("token");
        if (token != null && !token.isEmpty()) {
            String username = JwtUtil.getUsername(token);
            User admin = userService.getUserByUsername(username);
            if (admin != null) {
                model.put("user", admin);
            }
        }
        addPopupFromSession(req, model);
    }

	public ModelAndView hotels(Request req, Response res) {
        List<Hotel> hotels = hotelService.findAllHotels();
        HashMap<String, Object> model = new HashMap<>();
		injectAdminData(req, model);
        model.put("title", "Hotel Management");
        model.put("hotels", hotels);
        model.put("navlinks", getAdminNavLinks());
        model.put("currentpath", req.pathInfo());

        return new ModelAndView(model, "pages/admin/hotel");
    }

	public ModelAndView createHotelForm(Request req, Response res) {
        HashMap<String, Object> model = new HashMap<>();
		injectAdminData(req, model);
        model.put("title", "Create Hotel");
        model.put("navlinks", getAdminNavLinks());
		model.put("currentpath", req.pathInfo());

        return new ModelAndView(model, "pages/admin/create-hotel");
    }

	 public Object createHotel(Request req, Response res) {
        String name = req.queryParams("name");
        String location = req.queryParams("location");
        hotelService.createHotel(name, location);
        res.redirect("/admin/hotels");

        return null;
    }

	public ModelAndView editHotelForm(Request req, Response res) {
        Long id = Long.valueOf(req.params("id"));
        Hotel hotel = hotelService.findHotelById(id);
        HashMap<String, Object> model = new HashMap<>();
		injectAdminData(req, model);
        model.put("title", "Edit Hotel");
        model.put("hotel", hotel);
        model.put("navlinks", getAdminNavLinks());
		model.put("currentpath", req.pathInfo());

        return new ModelAndView(model, "pages/admin/edit-hotel");
    }

	public Object updateHotel(Request req, Response res) {
        Long id = Long.valueOf(req.params("id"));
        String name = req.queryParams("name");
        String location = req.queryParams("location");
        hotelService.updateHotel(id, name, location);
        res.redirect("/admin/hotels");

        return null;
    }

	public Object deleteHotel(Request req, Response res) {
        Long id = Long.valueOf(req.params("id"));
        hotelService.deleteHotel(id);
        res.redirect("/admin/hotels");

        return null;
    }

	public ModelAndView roomsByHotel(Request req, Response res) {
        Long hotelId = Long.valueOf(req.params("hotelId"));
        Hotel hotel = hotelService.findHotelById(hotelId);
        List<HotelRoom> rooms = hotelService.findRoomsByHotelId(hotelId);
        HashMap<String, Object> model = new HashMap<>();
		injectAdminData(req, model);
        model.put("hotel", hotel);
        model.put("rooms", rooms);
        model.put("navlinks", getAdminNavLinks());
		model.put("currentpath", req.pathInfo());

        return new ModelAndView(model, "pages/admin/room");
    }

	public ModelAndView createRoomForm(Request req, Response res) {
        Long hotelId = Long.valueOf(req.params("hotelId"));
        HashMap<String, Object> model = new HashMap<>();
		injectAdminData(req, model);
        model.put("hotelId", hotelId);
        model.put("navlinks", getAdminNavLinks());
        model.put("currentpath", req.pathInfo());

        return new ModelAndView(model, "pages/admin/create-room");
    }

	public Object createRoom(Request req, Response res) {
        Long hotelId = Long.valueOf(req.params("hotelId"));
        String roomNumber = req.queryParams("roomNumber");
        BigDecimal price = new BigDecimal(req.queryParams("price"));
        roomService.createRoom(hotelId, roomNumber, price);
        res.redirect("/admin/hotels/" + hotelId + "/rooms");

        return null;
    }

	public ModelAndView editRoomForm(Request req, Response res) {
        Long id = Long.valueOf(req.params("id"));
        HotelRoom room = roomService.getRoomById(id);
        HashMap<String, Object> model = new HashMap<>();
		injectAdminData(req, model);
        model.put("room", room);
        model.put("navlinks", getAdminNavLinks());
		model.put("currentpath", req.pathInfo());

        return new ModelAndView(model, "pages/admin/edit-room");
    }

	public Object updateRoom(Request req, Response res) {
        Long id = Long.valueOf(req.params("id"));
        Long hotelId = Long.valueOf(req.queryParams("hotelId"));
        BigDecimal price = new BigDecimal(req.queryParams("price"));
        roomService.updateRoomPrice(id, price);
        res.redirect("/admin/hotels/" + hotelId + "/rooms");

        return null;
    }

	public Object deleteRoom(Request req, Response res) {
        Long id = Long.valueOf(req.params("id"));
        Long hotelId = Long.valueOf(req.queryParams("hotelId"));
        roomService.deleteRoom(id);
        res.redirect("/admin/hotels/" + hotelId + "/rooms");

        return null;
    }
}

