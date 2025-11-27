package com.hotelgo.controller.Views;

import static com.hotelgo.util.PopupUtil.addPopupFromSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.hotelgo.config.ThymeleafConfig;
import com.hotelgo.config.ThymeleafTemplateEngine;
import com.hotelgo.model.BookedHistory;
import com.hotelgo.model.User;
import com.hotelgo.service.BookingService;
import com.hotelgo.service.UserService;
import com.hotelgo.util.JwtUtil;
import com.hotelgo.util.NavLinkUtil;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class ReceptionistViewController {

    private final ThymeleafTemplateEngine engine;
    private final BookingService bookingService;
    private final UserService userService;

    public ReceptionistViewController() {
        this.engine = ThymeleafConfig.createTemplateEngine();
        this.bookingService = new BookingService();
        this.userService = new UserService();
    }

    private User getUserFromSession(Request req) {
        String token = req.session().attribute("token");
        if (token != null && !token.isEmpty()) {
            String username = JwtUtil.getUsername(token);
            return userService.getUserByUsername(username);
        }
        return null;
    }

    private void injectCommonData(Request req, HashMap<String, Object> model) {
        User user = getUserFromSession(req);
        model.put("user", user);
        model.put("navLinks", NavLinkUtil.getNavLinks(user.getRole()));
        addPopupFromSession(req, model);
    }

    public ModelAndView dashboard(Request req, Response res) {
        HashMap<String, Object> model = new HashMap<>();
        injectCommonData(req, model);
        int page = 1;
        final int limit = 5;
        try {
            String pageParam = req.queryParams("page");
            if (pageParam != null) page = Integer.parseInt(pageParam);
        } catch (NumberFormatException ignored) {}

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

        int totalPages = Math.max(1, (int) Math.ceil((double) totalItems / limit));
        if (page > totalPages) page = totalPages;

        List<Integer> pages = new ArrayList<>();
        for (int i = 1; i <= totalPages; i++) pages.add(i);

        model.put("bookings", bookings);
        model.put("currentPage", page);
        model.put("limit", limit);
        model.put("totalItems", totalItems);
        model.put("totalPages", totalPages);
        model.put("pages", pages);
        model.put("title", "Receptionist Dashboard");
        model.put("currentPath", req.pathInfo());

        return new ModelAndView(model, "pages/receptionist/dashboard");
    }
}
