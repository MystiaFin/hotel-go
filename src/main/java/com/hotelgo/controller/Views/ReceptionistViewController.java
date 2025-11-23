package com.hotelgo.controller.Views;

import com.hotelgo.config.ThymeleafConfig;
import com.hotelgo.config.ThymeleafTemplateEngine;
import com.hotelgo.service.BookingService;
import com.hotelgo.service.UserService;
import com.hotelgo.util.JwtUtil;
import com.hotelgo.model.User;
import com.hotelgo.model.SideNavLinks;
import com.hotelgo.model.BookedHistory;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class ReceptionistViewController {
    private final ThymeleafTemplateEngine engine;
    private final BookingService bookingService;
    private final UserService userService;

    public ReceptionistViewController() {
        this.engine = ThymeleafConfig.createTemplateEngine();
        this.bookingService = new BookingService();
        this.userService = new UserService();
    }

    private List<SideNavLinks> getReceptionistLinks() {
        List<SideNavLinks> links = new ArrayList<>();
        links.add(new SideNavLinks("/", "Home"));
        links.add(new SideNavLinks("/booking/active", "Bookings Active"));
        links.add(new SideNavLinks("/history", "History"));
        links.add(new SideNavLinks("/receptionist/dashboard", "Receptionist Dashboard"));
        return links;
    }

    public ModelAndView dashboard(Request req, Response res) {
        HashMap<String, Object> model = new HashMap<>();
        
        String token = req.session().attribute("token");
        if (token != null && !token.isEmpty()) {
            String username = JwtUtil.getUsername(token);
            User user = userService.getUserByUsername(username);
            model.put("user", user);
        }

        String searchQuery = req.queryParams("search");
        List<BookedHistory> bookings;

        if (searchQuery != null && !searchQuery.trim().isEmpty()) {
            bookings = bookingService.searchBookings(searchQuery);
            model.put("searchQuery", searchQuery);
        } else {
            bookings = bookingService.getAllBookings();
        }

        model.put("bookings", bookings);
        model.put("currentPath", req.pathInfo());
        model.put("navLinks", getReceptionistLinks());
        model.put("title", "Receptionist Dashboard");
        
        return new ModelAndView(model, "pages/receptionist/dashboard");
    }
}