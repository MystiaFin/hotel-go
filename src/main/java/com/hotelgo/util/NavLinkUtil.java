package com.hotelgo.util;

import java.util.ArrayList;
import java.util.List;

import com.hotelgo.model.SideNavLinks;

public class NavLinkUtil {
    public static List<SideNavLinks> getNavLinks(String role) {
        List<SideNavLinks> links = new ArrayList<>();

        if ("CUSTOMER".equalsIgnoreCase(role)) {
            links.add(new SideNavLinks("/", "Home"));
            links.add(new SideNavLinks("/booking/active", "Bookings Active"));
            links.add(new SideNavLinks("/history", "Bookings History"));
        
        } else if ("RECEPTIONIST".equalsIgnoreCase(role)) {
            links.add(new SideNavLinks("/", "Booking Room Customer"));
            links.add(new SideNavLinks("/receptionist/dashboard", "Receptionist Dashboard"));
        
        } else if ("ADMIN".equalsIgnoreCase(role)) {
            links.add(new SideNavLinks("/admin/dashboard", "Dashboard Admin"));
            links.add(new SideNavLinks("/admin/hotels", "Hotel Management"));
        }

        return links;
    }
}
