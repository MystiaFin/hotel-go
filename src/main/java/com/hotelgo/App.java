package com.hotelgo;

import static spark.Spark.*;

import com.hotelgo.config.DatabaseConfig;
import com.hotelgo.controller.AuthController;
import com.hotelgo.controller.PageController;

public class App {
  public static void main(String[] args) {
    // Run database migrations first
    System.out.println("Starting HotelGo application...");
    System.out.println("Running database migrations...");

    try {
      DatabaseConfig.runMigrations();
    } catch (Exception e) {
      System.err.println("Failed to initialize database: " + e.getMessage());
      System.exit(1);
    }

    port(4567);

    staticFiles.location("/public");

    get(
        "/",
        (req, res) -> {
          res.type("application/json");
          return "{\"message\": \"HotelGo API is running!\", \"status\": \"success\"}";
        });

    PageController.initRoutes();
    AuthController.initRoutes();

    System.out.println("HotelGo application started successfully on http://localhost:4567");
  }
}
