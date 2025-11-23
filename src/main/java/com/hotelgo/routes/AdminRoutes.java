package com.hotelgo.routes;

import static spark.Spark.*;
import com.hotelgo.config.ThymeleafTemplateEngine;
import com.hotelgo.controller.Views.AdminViewController;
import com.hotelgo.middleware.AuthMiddleware;

public class AdminRoutes {

    public static void configure(ThymeleafTemplateEngine engine) {

        AdminViewController adminViewController = new AdminViewController();

        before("/admin/*", AuthMiddleware.authorize("ADMIN"));

        get("/admin/dashboard", adminViewController::adminDashboard, engine);

        get("/admin/hotels", adminViewController::hotels, engine);
        get("/admin/hotels/create", adminViewController::createHotelForm, engine);
        post("/admin/hotels/create", adminViewController::createHotel);

        get("/admin/hotels/:id/edit", adminViewController::editHotelForm, engine);
        post("/admin/hotels/:id/update", adminViewController::updateHotel);
        post("/admin/hotels/:id/delete", adminViewController::deleteHotel);

        get("/admin/hotels/:hotelId/rooms", adminViewController::roomsByHotel, engine);
        get("/admin/hotels/:hotelId/rooms/new", adminViewController::createRoomForm, engine);
        post("/admin/hotels/:hotelId/rooms/create", adminViewController::createRoom);

        get("/admin/rooms/:id/edit", adminViewController::editRoomForm, engine);
        post("/admin/rooms/:id/update", adminViewController::updateRoom);
        post("/admin/rooms/:id/delete", adminViewController::deleteRoom);
    }
}
