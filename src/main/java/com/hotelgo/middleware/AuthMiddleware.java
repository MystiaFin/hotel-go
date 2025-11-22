package com.hotelgo.middleware;

import com.hotelgo.util.JwtUtil;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import spark.Request;
import spark.Response;
import static spark.Spark.halt;

public class AuthMiddleware {
    public static spark.Filter authorize(String... allowedRoles) {
        return (Request req, Response res) -> {
            String token = req.session().attribute("token");

            boolean unauthorized = false;
            String message = "";

            if (token == null) {
                unauthorized = true;
                message = "Access denied. You must login first";
            } else {
                try {
                    String role = JwtUtil.getRole(token);
                    boolean allowed = false;
                    for (String r : allowedRoles) {
                        if (r.equalsIgnoreCase(role)) {
                            allowed = true;
                            break;
                        }
                    }
                    if (!allowed) {
                        unauthorized = true;
                        message = "Access denied. Your role does not have permission to access this page";
                    }
                } catch (ExpiredJwtException e) {
                    unauthorized = true;
                    message = "Your session has expired. Please login again";
                } catch (JwtException e) {
                    unauthorized = true;
                    message = "Invalid token. Please login again";
                }
            }

            if (unauthorized) {
                req.session().attribute("popupMessage", message);
                req.session().attribute("popupType", "error");
                req.session().attribute("redirectUrl", "/login");

                res.redirect("/login");
                halt();
            }
        };
    }
}
