package com.hotelgo.controller;

import com.hotelgo.model.User;
import com.hotelgo.service.UserService;
import com.hotelgo.util.JwtUtil;
import spark.Request;
import spark.Response;

public class AuthController {
    private final UserService userService = new UserService();
    
    public Object register(Request req, Response res) {
        User user = new User();
        user.setName(req.queryParams("name"));
        user.setEmail(req.queryParams("email"));
        user.setUsername(req.queryParams("username"));
        user.setPassword(req.queryParams("password"));
        user.setRole("CUSTOMER");
        String msg = userService.register(user);

        req.session(true);
        if (msg.equals("Registration successful")) {
            req.session().attribute("popupMessage", msg);
            req.session().attribute("popupType", "success");
            req.session().attribute("redirectUrl", "/login");
        } else {
            req.session().attribute("popupMessage", msg);
            req.session().attribute("popupType", "error");
            req.session().attribute("redirectUrl", "/register");
        }
        res.redirect("/register");
        return null;
    }

    public Object login(Request req, Response res) {
        String email = req.queryParams("email");
        String password = req.queryParams("password");
        String token = userService.login(email, password);

        req.session(true);
        if (token == null) {
            req.session().attribute("popupMessage", "Incorrect email or password");
            req.session().attribute("popupType", "error");
            req.session().attribute("redirectUrl", "/login");
        } else {
            req.session().attribute("token", token);
            req.session().attribute("email", email);
            String role = JwtUtil.getRole(token);
            switch (role.toLowerCase()) {
                case "admin":
                    res.redirect("/admin/dashboard");
                    break;
                case "receptionist":
                    res.redirect("/receptionist/dashboard");
                    break;
                default:
                    res.redirect("/");
                    break;
            }
            return null;
        }
        res.redirect("/login");
        return null;
    }

    public Object forgotPassword(Request req, Response res) {
        String email = req.queryParams("email");
        String newPassword = req.queryParams("password");
        String msg = userService.forgotPassword(email, newPassword);

        req.session(true);
        if (msg.equals("Password updated successfully")) {
            req.session().attribute("popupMessage", msg);
            req.session().attribute("popupType", "success");
            req.session().attribute("redirectUrl", "/login");
        } else {
            req.session().attribute("popupMessage", msg);
            req.session().attribute("popupType", "error");
            req.session().attribute("redirectUrl", "/forgot-password");
        }
        res.redirect("/forgot-password");
        return null;
    }

    public Object updateProfile(Request req, Response res) {
        String username = JwtUtil.getUsername(req.session().attribute("token"));
        User u = new User();
        u.setUsername(username);
        u.setName(req.queryParams("name"));
        u.setEmail(req.queryParams("email"));
        String msg = userService.updateUser(u);
        req.session(true);
        if (msg.startsWith("SUCCESS")) {
            req.session().attribute("popupMessage", msg.substring(8));
            req.session().attribute("popupType", "success");
            req.session().attribute("redirectUrl", "/profile");
        } else {
            req.session().attribute("popupMessage", msg.substring(7));
            req.session().attribute("popupType", "error");
            req.session().attribute("redirectUrl", "/profile");
        }
        res.redirect("/profile");
        return null;
    }
}
