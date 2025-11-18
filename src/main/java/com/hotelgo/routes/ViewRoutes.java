package com.hotelgo.routes;
import static spark.Spark.*;
import com.hotelgo.config.ThymeleafTemplateEngine;
import com.hotelgo.controller.Views.ViewController;
import com.hotelgo.controller.Views.ClientViewController;

public class ViewRoutes {
    
    public static void configure(ThymeleafTemplateEngine engine) {
        ViewController viewController = new ViewController();
        ClientViewController clientViewController = new ClientViewController();
        
        // Public pages
        get("/login", viewController::login, engine);
        get("/register", viewController::register, engine);
        get("/forgot-password", viewController::forgotPassword, engine);

				// Client pages
        get("/", clientViewController::home, engine);
        
    }
}
