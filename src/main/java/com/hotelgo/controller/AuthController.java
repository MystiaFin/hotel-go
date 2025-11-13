package com.hotelgo.controller;

import com.hotelgo.model.User;
import com.hotelgo.service.UserService;
import static spark.Spark.*;

public class AuthController {
    private static final UserService userService = new UserService();

    public static void initRoutes() {
        path("/api/user", () -> {
            post("/register", (req, res) -> {
                User user = new User();
                user.setNama(req.queryParams("nama"));
                user.setEmail(req.queryParams("email"));
                user.setUsername(req.queryParams("username"));
                user.setPassword(req.queryParams("password"));
                user.setRole("CUSTOMER");

                String msg = userService.register(user);
                if (msg.equals("Registrasi berhasil!")) {
                    res.redirect("/login");
                } else {
                    res.status(400);
                    return msg;
                }
                return null;
            });

            post("/login", (req, res) -> {
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
            });

            post("/forgot-password", (req, res) -> {
                String email = req.queryParams("email");
                String newPassword = req.queryParams("password");
                String msg = userService.forgotPassword(email, newPassword);

                if (msg.equals("Password berhasil diperbarui"))
                    res.redirect("/login");
                else {
                    res.status(400);
                    return msg;
                }
                return null;
            });
        });
    }
}
