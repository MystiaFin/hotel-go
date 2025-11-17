package com.hotelgo.routes;

import static spark.Spark.*;
import com.hotelgo.controller.ViewController;
import com.hotelgo.middleware.AuthMiddleware;

public class ViewRoutes {
	private static final ViewController viewController = new ViewController();

	public static void configure() {
		// Public pages
		get("/", viewController::home);
		get("/login", viewController::login);
		get("/register", viewController::register);
		get("/forgot-password", viewController::forgotPassword);

		// Protected pages
		before("/dashboard", AuthMiddleware.authorize("CUSTOMER", "ADMIN", "RESEPSIONIS"));
		get("/dashboard", viewController::dashboard);
	}
}
