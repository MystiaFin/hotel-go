package com.hotelgo.routes;

import com.hotelgo.config.ThymeleafConfig;
import com.hotelgo.config.ThymeleafTemplateEngine;

public class Routes {

    public static void init() {
        ThymeleafTemplateEngine engine = ThymeleafConfig.createTemplateEngine();

        ViewRoutes.configure(engine);
        ApiRoutes.configure();
    }
}
