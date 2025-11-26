package com.hotelgo.controller.Views;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

import com.hotelgo.model.User;
import com.hotelgo.service.UserService;
import com.hotelgo.util.JwtUtil;
import com.hotelgo.util.NavLinkUtil;

import java.util.HashMap;
import static com.hotelgo.util.PopupUtil.addPopupFromSession;

public class ViewController {
	private final UserService userService;

	public ViewController() {
		this.userService = new UserService();
	}

    public ModelAndView login(Request req, Response res) {
        HashMap<String, Object> model = new HashMap<>();
        addPopupFromSession(req, model);
        return new ModelAndView(model, "pages/auth/login");
    }

    public ModelAndView register(Request req, Response res) {
        HashMap<String, Object> model = new HashMap<>();
        addPopupFromSession(req, model);
        return new ModelAndView(model, "pages/client/register");
    }

    public ModelAndView forgotPassword(Request req, Response res) {
        HashMap<String, Object> model = new HashMap<>();
        addPopupFromSession(req, model);
        return new ModelAndView(model, "pages/auth/forgot-password");
    }

    public ModelAndView profile(Request req, Response res) {
        HashMap<String, Object> model = new HashMap<>();
        String username = JwtUtil.getUsername(req.session().attribute("token"));
        User user = userService.getUserByUsername(username);
        model.put("user", user);
        model.put("title", "Profile");
        model.put("currentPath", req.pathInfo());
        model.put("navLinks", NavLinkUtil.getNavLinks(user.getRole()));
        addPopupFromSession(req, model);
        return new ModelAndView(model, "pages/client/profile");
    }
}
