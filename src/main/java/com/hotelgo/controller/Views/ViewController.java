package com.hotelgo.controller.Views;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;

public class ViewController {
    
    
    public ModelAndView login(Request req, Response res) {
        return new ModelAndView(new HashMap<>(), "pages/auth/login");
    }
    
    public ModelAndView register(Request req, Response res) {
        return new ModelAndView(new HashMap<>(), "pages/client/register");
    }
    
    public ModelAndView forgotPassword(Request req, Response res) {
        return new ModelAndView(new HashMap<>(), "pages/auth/forgot-password");
    }

}
