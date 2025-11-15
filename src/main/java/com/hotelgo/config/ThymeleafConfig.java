package com.hotelgo.config;

import nz.net.ultraq.thymeleaf.layoutdialect.LayoutDialect;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

public class ThymeleafConfig {

  public static ThymeleafTemplateEngine createTemplateEngine() {
    ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
    resolver.setPrefix("templates/");
    resolver.setSuffix(".html");
    resolver.setTemplateMode("HTML");
    resolver.setCharacterEncoding("UTF-8");
    resolver.setCacheable(false);

    TemplateEngine templateEngine = new TemplateEngine();
    templateEngine.setTemplateResolver(resolver);
    templateEngine.addDialect(new LayoutDialect());

    return new ThymeleafTemplateEngine(templateEngine);
  }
}
