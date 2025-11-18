package com.hotelgo.routes;

import static spark.Spark.*;
import com.hotelgo.config.ThymeleafTemplateEngine;
import com.hotelgo.controller.ClientViewController;
import com.hotelgo.controller.ViewController;
import com.hotelgo.middleware.AuthMiddleware;

public class ViewRoutes {
    
    public static void configure(ThymeleafTemplateEngine engine) {
        ViewController viewController = new ViewController();
        ClientViewController clientViewController = new ClientViewController();

        get("/login", viewController::login, engine);
        get("/register", viewController::register, engine);
        get("/forgot-password", viewController::forgotPassword, engine);

        before("/dashboard", AuthMiddleware.authorize("CUSTOMER", "ADMIN", "RESEPSIONIS"));
        get("/dashboard", viewController::dashboard, engine);

        before("/hotels/:id", AuthMiddleware.authorize("CUSTOMER", "ADMIN", "RESEPSIONIS"));
        get("/hotels/:id", clientViewController::hotelRooms, engine);

        before("/booking/confirm/:roomId", AuthMiddleware.authorize("CUSTOMER", "ADMIN", "RESEPSIONIS"));
        get("/booking/confirm/:roomId", clientViewController::confirmBooking, engine);
    }
}
