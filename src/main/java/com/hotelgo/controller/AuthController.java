package com.hotelgo.controller;

import com.hotelgo.model.User;
import com.hotelgo.service.UserService;
import spark.Request;
import spark.Response;

public class AuthController {
    private final UserService userService = new UserService();
    
    public Object register(Request req, Response res) {
        User user = new User();
        user.setNama(req.queryParams("nama"));
        user.setEmail(req.queryParams("email"));
        user.setUsername(req.queryParams("username"));
        user.setPassword(req.queryParams("password"));
        user.setRole("CUSTOMER");
        
        String msg = userService.register(user);
        
        if (msg.equals("Registrasi berhasil!")) {
            res.redirect("/login");
            return null;
        } else {
            res.status(400);
            return msg;
        }
    }
    
    public Object login(Request req, Response res) {
        String email = req.queryParams("email");
        String password = req.queryParams("password");
        String token = userService.login(email, password);
        
        if (token == null) {
            res.status(401);
            return "Email atau password salah";
        }
        
        req.session().attribute("token", token);
        req.session().attribute("email", email);
        res.redirect("/dashboard");
        return null;
    }
    
    public Object forgotPassword(Request req, Response res) {
        String email = req.queryParams("email");
        String newPassword = req.queryParams("password");
        String msg = userService.forgotPassword(email, newPassword);
        
        if (msg.equals("Password berhasil diperbarui")) {
            res.redirect("/login");
            return null;
        } else {
            res.status(400);
            return msg;
        }
    }
}
