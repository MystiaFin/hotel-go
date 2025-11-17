package com.hotelgo.middleware;

import com.hotelgo.util.JwtUtil;
import io.jsonwebtoken.JwtException;
import spark.Request;
import spark.Response;
import static spark.Spark.halt;

public class AuthMiddleware {
    public static spark.Filter authorize(String... allowedRoles) {
        return (Request req, Response res) -> {
            String token = req.session().attribute("token");
            if (token == null) {
                res.status(401);
                res.redirect("/login");
            }

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
                    res.status(403);
                    halt(403, "Akses ditolak");
                }
            } catch (JwtException e) {
                res.status(401);
                halt(401, "Token tidak valid");
            }
        };
    }
}
