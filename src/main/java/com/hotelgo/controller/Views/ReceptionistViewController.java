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

        // default pagination values
        int page = 1;
        final int limit = 10; // fixed as you requested (option A)

        try {
            String pageParam = req.queryParams("page");
            if (pageParam != null) {
                page = Integer.parseInt(pageParam);
            }
        } catch (NumberFormatException e) {
            page = 1;
        }

        String searchQuery = req.queryParams("search");
        List<BookedHistory> bookings;
        int totalItems = 0;

        if (searchQuery != null && !searchQuery.trim().isEmpty()) {
            bookings = bookingService.searchBookingsPaginated(searchQuery.trim(), page, limit);
            totalItems = bookingService.getSearchBookingsCount(searchQuery.trim());
            model.put("searchQuery", searchQuery.trim());
        } else {
            bookings = bookingService.getBookingsPaginated(page, limit);
            totalItems = bookingService.getTotalBookingsCount();
        }

        int totalPages = (int) Math.ceil((double) totalItems / limit);
        if (totalPages < 1) totalPages = 1;
        if (page > totalPages) page = totalPages;

        // create page numbers list for view
        List<Integer> pages = new ArrayList<>();
        for (int i = 1; i <= totalPages; i++) {
            pages.add(i);
        }

        model.put("bookings", bookings);
        model.put("currentPage", page);
        model.put("limit", limit);
        model.put("totalItems", totalItems);
        model.put("totalPages", totalPages);
        model.put("pages", pages);

        model.put("currentPath", req.pathInfo());
        model.put("navLinks", getReceptionistLinks());
        model.put("title", "Receptionist Dashboard");
        
        return new ModelAndView(model, "pages/receptionist/dashboard");
    }
}
