package com.hotelgo.controller;

import com.hotelgo.config.ThymeleafConfig;
import com.hotelgo.config.ThymeleafTemplateEngine;
import com.hotelgo.model.User;
import com.hotelgo.service.HotelService;
import com.hotelgo.service.UserService;
import com.hotelgo.util.JwtUtil;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;

public class ViewController {
    private final ThymeleafTemplateEngine engine;
    private final UserService userService;
    private final HotelService hotelService;

    public ViewController() {
        this.engine = ThymeleafConfig.createTemplateEngine();
        this.userService = new UserService();
        this.hotelService = new HotelService();
    }
    
    public ModelAndView login(Request req, Response res) {
        return new ModelAndView(new HashMap<>(), "pages/client/login");
    }
    
    public ModelAndView register(Request req, Response res) {
        return new ModelAndView(new HashMap<>(), "pages/client/register");
    }
    
    public ModelAndView forgotPassword(Request req, Response res) {
        return new ModelAndView(new HashMap<>(), "pages/client/forgot-password");
    }
    
    public ModelAndView dashboard(Request req, Response res) {
        HashMap<String, Object> model = new HashMap<>();
        String token = req.session().attribute("token");
        String username = JwtUtil.getUsername(token);
        User user = userService.getUserByUsername(username);
        model.put("nama", user.getNama());
        model.put("email", user.getEmail());
        model.put("role", user.getRole());
        model.put("hotels", hotelService.findAllHotels());
        model.put("title", "Dashboard");
        return new ModelAndView(model, "pages/client/dashboard");
    }

    public ModelAndView profile(Request req, Response res) {
        HashMap<String, Object> model = new HashMap<>();
        String username = JwtUtil.getUsername(req.session().attribute("token"));
        User user = userService.getUserByUsername(username);
        model.put("user", user);
        return new ModelAndView(model, "pages/client/profile");
    }
    
}
