package com.hotelgo;

import static spark.Spark.*;

// Necessary utils imports
import com.hotelgo.config.DatabaseConfig;
import com.hotelgo.config.ThymeleafConfig;
import com.hotelgo.config.ThymeleafTemplateEngine;

// Route imports
import com.hotelgo.routes.ClientRoutes;

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

    ThymeleafTemplateEngine engine = ThymeleafConfig.createTemplateEngine();
    ClientRoutes.configure(engine);

    System.out.println("HotelGo application started on http://localhost:4567");
  }
}
