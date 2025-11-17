package com.hotelgo.routes;

import static spark.Spark.*;
import com.hotelgo.config.ThymeleafTemplateEngine;
import com.hotelgo.controller.ViewController;
import com.hotelgo.middleware.AuthMiddleware;

public class ViewRoutes {
    
    public static void configure(ThymeleafTemplateEngine engine) {
        ViewController viewController = new ViewController();
        
        get("/", viewController::home, engine);
        get("/login", viewController::login, engine);
        get("/register", viewController::register, engine);
        get("/forgot-password", viewController::forgotPassword, engine);
        
        // Protected pages
        before("/dashboard", AuthMiddleware.authorize("CUSTOMER", "ADMIN", "RESEPSIONIS"));
        get("/dashboard", viewController::dashboard, engine);
    }
}
