package com.hotelgo.controller.Views;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;

public class ViewController {

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
		return new ModelAndView(model, "pages/client/profile");
	}

}
