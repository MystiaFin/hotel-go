package com.hotelgo.controller.Views;
import com.hotelgo.model.SideNavLinks;
import java.util.List;
import java.util.ArrayList;

public class ReceptionistViewController {
	private List<SideNavLinks> getAdminNavLinks() {
		List<SideNavLinks> links = new ArrayList<>();
		links.add(new SideNavLinks("/receptionist/dashboard", "Dashboard")); // home page to view available hotels
		links.add(new SideNavLinks("/receptionist/hotel", "Hotel Management")); // view available hotels and the room details
		return links;
	}

}

