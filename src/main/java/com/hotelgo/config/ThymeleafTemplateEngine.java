package com.hotelgo.config;

import java.util.Map;
import org.thymeleaf.context.Context;
import spark.ModelAndView;

public class ThymeleafTemplateEngine extends spark.TemplateEngine {

  private final org.thymeleaf.TemplateEngine templateEngine;

  public ThymeleafTemplateEngine(org.thymeleaf.TemplateEngine templateEngine) {
    this.templateEngine = templateEngine;
  }

  @Override
  public String render(ModelAndView modelAndView) {
    Context context = new Context();

    if (modelAndView.getModel() instanceof Map) {
      context.setVariables((Map<String, Object>) modelAndView.getModel());
    }

    return templateEngine.process(modelAndView.getViewName(), context);
  }
}
