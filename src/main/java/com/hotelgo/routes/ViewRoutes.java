package com.hotelgo.routes;
import static spark.Spark.*;
import com.hotelgo.config.ThymeleafTemplateEngine;
import com.hotelgo.controller.Views.ViewController;
import com.hotelgo.controller.Views.ClientViewController;
import com.hotelgo.controller.Views.AdminViewController;
import com.hotelgo.middleware.AuthMiddleware;

public class ViewRoutes {
    
    public static void configure(ThymeleafTemplateEngine engine) {
        ViewController viewController = new ViewController();
        ClientViewController clientViewController = new ClientViewController();
        AdminViewController adminViewController = new AdminViewController();
        
				// Client View Routes
        get("/login", viewController::login, engine);
        get("/register", viewController::register, engine);
        get("/forgot-password", viewController::forgotPassword, engine);
        
        before("/", AuthMiddleware.authorize("CUSTOMER", "ADMIN", "RESEPSIONIS"));
        get("/", clientViewController::home, engine);

        before("/hotels/:id", AuthMiddleware.authorize("CUSTOMER", "ADMIN", "RESEPSIONIS"));
        get("/hotels/:id", clientViewController::hotelRooms, engine);

        before("/booking/confirm/:roomId", AuthMiddleware.authorize("CUSTOMER", "ADMIN", "RESEPSIONIS"));
        get("/booking/confirm/:roomId", clientViewController::confirmBooking, engine);

        before("/booking/active", AuthMiddleware.authorize("CUSTOMER", "ADMIN", "RESEPSIONIS"));
        get("/booking/active", clientViewController::activeBookings, engine);

        before("/profile", AuthMiddleware.authorize("CUSTOMER", "ADMIN", "RESEPSIONIS"));
        get("/profile", viewController::profile, engine);

        get("/logout", (req, res) -> {
            req.session().removeAttribute("token");
            req.session().invalidate();
            res.redirect("/login");
            return null;
        });
				
				// Admin View Routes
        before("/admin/*", AuthMiddleware.authorize("ADMIN"));

				get("/admin/dashboard", adminViewController::adminDashboard, engine);
        
        get("/admin/hotels", adminViewController::hotels, engine);
        get("/admin/hotels/create", adminViewController::createHotelForm, engine);
        post("/admin/hotels/create", adminViewController::createHotel);
        get("/admin/hotels/:id/edit", adminViewController::editHotelForm, engine);
        post("/admin/hotels/:id/update", adminViewController::updateHotel);
        post("/admin/hotels/:id/delete", adminViewController::deleteHotel);

        get("/admin/rooms", adminViewController::roomsByHotel, engine);
        get("/admin/hotels/:hotelId/rooms", adminViewController::roomsByHotel, engine);
        get("/admin/hotels/:hotelId/rooms/new", adminViewController::createRoomForm, engine);
        post("/admin/hotels/:hotelId/rooms/create", adminViewController::createRoom);
        get("/admin/rooms/:id/edit", adminViewController::editRoomForm, engine);
        post("/admin/rooms/:id/update", adminViewController::updateRoom);
        post("/admin/rooms/:id/delete", adminViewController::deleteRoom);
    }
}
