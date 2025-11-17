package com.hotelgo.controller;

import com.hotelgo.config.ThymeleafConfig;
import com.hotelgo.config.ThymeleafTemplateEngine;
import com.hotelgo.model.User;
import com.hotelgo.repository.UserRepository;
import com.hotelgo.util.JwtUtil;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;

public class ViewController {
    private final ThymeleafTemplateEngine engine;
    private final UserRepository userRepository;
    
    public ViewController() {
        this.engine = ThymeleafConfig.createTemplateEngine();
        this.userRepository = new UserRepository();
    }
    
    public ModelAndView home(Request req, Response res) {
        HashMap<String, Object> model = new HashMap<>();
        model.put("title", "Home Page");
        return new ModelAndView(model, "pages/client/home");
    }
    
    public ModelAndView login(Request req, Response res) {
        return new ModelAndView(new HashMap<>(), "login");
    }
    
    public ModelAndView register(Request req, Response res) {
        return new ModelAndView(new HashMap<>(), "register");
    }
    
    public ModelAndView forgotPassword(Request req, Response res) {
        return new ModelAndView(new HashMap<>(), "forgot-password");
    }
    
    public ModelAndView dashboard(Request req, Response res) {
        HashMap<String, Object> model = new HashMap<>();
        
        String token = req.session().attribute("token");
        String username = JwtUtil.getUsername(token);
        User user = userRepository.findByUsername(username);
        
        model.put("nama", user.getNama());
        model.put("email", user.getEmail());
        model.put("role", user.getRole());
        
        return new ModelAndView(model, "dashboard");
    }
}
