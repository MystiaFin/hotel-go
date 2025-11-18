package com.hotelgo;

import static spark.Spark.*;
import com.hotelgo.config.DatabaseConfig;
import com.hotelgo.config.ThymeleafConfig;
import com.hotelgo.config.ThymeleafTemplateEngine;
import com.hotelgo.routes.ViewRoutes;
import com.hotelgo.routes.ApiRoutes;
import com.hotelgo.scheduler.BookingScheduler;

public class App {
	public static void main(String[] args) {
		System.out.println("Starting HotelGo application...");
		System.out.println("Running database migrations...");

		// Running database migrations and seeding
		try {
			DatabaseConfig.runMigrations();
		} catch (Exception e) {
			System.err.println("Failed to initialize database: " + e.getMessage());
			System.exit(1);
		}

		port(4567);
		staticFiles.location("/static");

		// Create template engine
		ThymeleafTemplateEngine engine = ThymeleafConfig.createTemplateEngine();

		// Configure routes - pass engine to ViewRoutes
		ViewRoutes.configure(engine);
		ApiRoutes.configure();

        // Scheduler update status booking EXPIRED
        BookingScheduler.start();
		System.out.println("HotelGo application started on http://localhost:4567");
	}
}
