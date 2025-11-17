package com.hotelgo.routes;

import com.hotelgo.config.ThymeleafTemplateEngine;
import com.hotelgo.config.ThymeleafConfig;

public class Routes {
	public static void init() {

    ThymeleafTemplateEngine engine = ThymeleafConfig.createTemplateEngine();

		ViewRoutes.configure(engine);
		ApiRoutes.configure();

	}
}
