package com.hotelgo.controller;

import com.hotelgo.middleware.AuthMiddleware;
import com.hotelgo.model.User;
import com.hotelgo.repository.UserRepository;
import com.hotelgo.util.JwtUtil;
import spark.ModelAndView;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import java.util.HashMap;
import static spark.Spark.*;

public class PageController {
    public static void initRoutes() {
        ThymeleafTemplateEngine engine = new ThymeleafTemplateEngine();

        get("/login", (req, res) -> new ModelAndView(new HashMap<>(), "login"), engine);
        get("/register", (req, res) -> new ModelAndView(new HashMap<>(), "register"), engine);
        get("/forgot-password", (req, res) -> new ModelAndView(new HashMap<>(), "forgot-password"), engine);

        before("/dashboard", AuthMiddleware.authorize("CUSTOMER", "ADMIN", "RESEPSIONIS"));
        get("/dashboard", (req, res) -> {
            HashMap<String, Object> model = new HashMap<>();
            String username = JwtUtil.getUsername(req.session().attribute("token"));
            User user = new UserRepository().findByUsername(username);
            model.put("nama", user.getNama());
            model.put("email", user.getEmail());
            model.put("role", user.getRole());
            return new ModelAndView(model, "dashboard");
        }, engine);
    }
}
