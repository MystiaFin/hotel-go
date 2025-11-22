package com.hotelgo.util;

import spark.Request;
import java.util.HashMap;

public class PopupUtil {

    public static void addPopupFromSession(Request req, HashMap<String, Object> model) {
        if (req.session(false) == null) {
            return;
        }
        String popupMessage = req.session().attribute("popupMessage");
        String popupType = req.session().attribute("popupType");
        String redirectUrl = req.session().attribute("redirectUrl");
        if (popupMessage != null && popupType != null) {
            model.put("popupMessage", popupMessage);
            model.put("popupType", popupType);
            model.put("redirectUrl", redirectUrl);
            req.session().removeAttribute("popupMessage");
            req.session().removeAttribute("popupType");
            req.session().removeAttribute("redirectUrl");
        }
    }
}
