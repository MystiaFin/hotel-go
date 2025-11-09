package com.hotelgo;

import static spark.Spark.*;

import java.util.HashMap;
import java.util.Map;
import spark.ModelAndView;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

public class App {
  public static void main(String[] args) {
    port(4567);

    get(
        "/",
        (req, res) -> {
          Map<String, Object> model = new HashMap<>();
          model.put("message", "Hello from Thymeleaf!");
          return new ModelAndView(model, "index");
        },
        new ThymeleafTemplateEngine());
  }
}
