package com.hotelgo.routes;

import static spark.Spark.*;
import com.hotelgo.controller.AuthController;
import com.hotelgo.controller.BookingController;
import com.hotelgo.controller.HotelController;

public class ApiRoutes {
	private static final AuthController authController = new AuthController();
    private static final HotelController hotelController = new HotelController();
    private static final BookingController bookingController = new BookingController();

	public static void configure() {
		path("/api/user", () -> {
			post("/register", authController::register);
			post("/login", authController::login);
			post("/forgot-password", authController::forgotPassword);
            post("/profile/update", authController::updateProfile);
		});
        path("/api/hotel", () -> {
            get("", hotelController::getHotels);
            get("/:id", hotelController::getRoomsByHotelId);
        });
        path("/api/booking", () -> {
            post("/create", bookingController::createBooking);
            get("/active", bookingController::getActiveBookings);
            get("/all", bookingController::getBookings);
            post("/cancel/:id", bookingController::cancelBooking);
            post("/approve", bookingController::approveBooking);
            post("/reject", bookingController::rejectBooking);
            post("/complete", bookingController::completeBooking);
        });
	}
}
