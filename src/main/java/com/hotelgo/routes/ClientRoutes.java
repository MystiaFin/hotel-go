package com.hotelgo.routes;

import static spark.Spark.*;

import com.hotelgo.config.ThymeleafTemplateEngine;
import java.util.HashMap;
import spark.ModelAndView;

public class ClientRoutes {

  public static void configure(ThymeleafTemplateEngine engine) {

    get(
        "/",
        (req, res) -> {
          HashMap<String, Object> model = new HashMap<>();
          model.put("title", "Home Page");
          return engine.render(new ModelAndView(model, "pages/home"));
        });

    get(
        "/health",
        (req, res) -> {
          res.type("application/json");
          return "{\"status\": \"healthy\", \"database\": \"connected\"}";
        });
  }
}
