package com.hotelgo.routes;

import static spark.Spark.*;
import com.hotelgo.controller.AuthController;

public class ApiRoutes {
	private static final AuthController authController = new AuthController();

	public static void configure() {
		path("/api/user", () -> {
			post("/register", authController::register);
			post("/login", authController::login);
			post("/forgot-password", authController::forgotPassword);
		});
	}
}
