package com.hotelgo.controller.Views;

import com.hotelgo.config.ThymeleafConfig;
import com.hotelgo.config.ThymeleafTemplateEngine;
import com.hotelgo.model.User;
import com.hotelgo.repository.UserRepository;
import com.hotelgo.util.JwtUtil;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import com.hotelgo.model.SideNavLinks;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class ClientViewController {
	private final ThymeleafTemplateEngine engine;
	private final UserRepository userRepository;

	public ClientViewController() {
		this.engine = ThymeleafConfig.createTemplateEngine();
		this.userRepository = new UserRepository();
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

		// ADD THIS CHECK
		if (token == null || token.isEmpty()) {
			res.redirect("/login");
			return null;
		}

		String username = JwtUtil.getUsername(token);
		User user = userRepository.findByUsername(username);

		model.put("nama", user.getNama());
		model.put("email", user.getEmail());
		model.put("role", user.getRole());
		model.put("currentPath", req.pathInfo());
		model.put("navLinks", getClientNavLinks());

		return new ModelAndView(model, "pages/client/home");
	}
}
