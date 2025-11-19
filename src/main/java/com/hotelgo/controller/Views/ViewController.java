package com.hotelgo.controller.Views;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

import com.hotelgo.model.SideNavLinks;
import com.hotelgo.model.User;
import com.hotelgo.service.UserService;
import com.hotelgo.util.JwtUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ViewController {
	private final UserService userService;

	public ViewController() {
		this.userService = new UserService();
	}

	private List<SideNavLinks> getClientNavLinks() {
		List<SideNavLinks> links = new ArrayList<>();
		links.add(new SideNavLinks("/", "Home"));
		links.add(new SideNavLinks("/booking/active", "Bookings Active"));
		links.add(new SideNavLinks("/history", "History"));
		return links;
	}

	public ModelAndView login(Request req, Response res) {
		return new ModelAndView(new HashMap<>(), "pages/auth/login");
	}

	public ModelAndView register(Request req, Response res) {
		return new ModelAndView(new HashMap<>(), "pages/client/register");
	}

	public ModelAndView forgotPassword(Request req, Response res) {
		return new ModelAndView(new HashMap<>(), "pages/auth/forgot-password");
	}

	public ModelAndView profile(Request req, Response res) {
		HashMap<String, Object> model = new HashMap<>();
		String username = JwtUtil.getUsername(req.session().attribute("token"));
		User user = userService.getUserByUsername(username);
		model.put("user", user);
		model.put("title", "Profile");
		model.put("currentPath", req.pathInfo());
		model.put("navLinks", getClientNavLinks());
		return new ModelAndView(model, "pages/client/profile");
	}

}
