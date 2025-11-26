package com.hotelgo.routes;
import static spark.Spark.*;
import com.hotelgo.config.ThymeleafTemplateEngine;
import com.hotelgo.controller.Views.ViewController;
import com.hotelgo.controller.Views.ClientViewController;
import com.hotelgo.controller.Views.ReceptionistViewController; 
import com.hotelgo.middleware.AuthMiddleware;

public class ViewRoutes {
    
    public static void configure(ThymeleafTemplateEngine engine) {
        ViewController viewController = new ViewController();
        ClientViewController clientViewController = new ClientViewController();
        ReceptionistViewController receptionistViewController = new ReceptionistViewController(); 
        
        get("/login", viewController::login, engine);
        get("/register", viewController::register, engine);
        get("/forgot-password", viewController::forgotPassword, engine);
        
        before("/", AuthMiddleware.authorize("CUSTOMER", "RECEPTIONIST"));
        get("/", clientViewController::home, engine);

        before("/hotels/:id", AuthMiddleware.authorize("CUSTOMER", "RECEPTIONIST"));
        get("/hotels/:id", clientViewController::hotelRooms, engine);

        before("/booking/confirm/:roomId", AuthMiddleware.authorize("CUSTOMER", "RECEPTIONIST"));
        get("/booking/confirm/:roomId", clientViewController::confirmBooking, engine);

        before("/booking/active", AuthMiddleware.authorize("CUSTOMER"));
        get("/booking/active", clientViewController::activeBookings, engine);

        before("/profile", AuthMiddleware.authorize("CUSTOMER", "ADMIN", "RECEPTIONIST"));
        get("/profile", viewController::profile, engine);

        get("/logout", (req, res) -> {
            req.session().removeAttribute("token");
            req.session().invalidate();
            res.redirect("/login");
            return null;
        });
      
        before("/history", AuthMiddleware.authorize("CUSTOMER"));
        get("/history", clientViewController::allBookings, engine);
				
		AdminRoutes.configure(engine);

        before("/receptionist/*", AuthMiddleware.authorize("RECEPTIONIST"));
        get("/receptionist/dashboard", receptionistViewController::dashboard, engine);
    }
}
