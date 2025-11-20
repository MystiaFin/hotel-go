package com.hotelgo.controller.Views;

import com.hotelgo.model.SideNavLinks;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class AdminViewController {
	private List<SideNavLinks> getAdminNavLinks() {
		List<SideNavLinks> links = new ArrayList<>();
		links.add(new SideNavLinks("/admin/dashboard", "Dashboard"));
		links.add(new SideNavLinks("/admin/hotel", "Hotel Management"));
		return links;
	}


	public ModelAndView adminDashboard(Request req, Response res) {
		HashMap<String, Object> model = new HashMap<>();
		model.put("title", "Admin Dashboard");
		model.put("currentPath", req.pathInfo());
		model.put("navLinks", getAdminNavLinks());

		return new ModelAndView(model, "pages/admin/dashboard");
	}

}

