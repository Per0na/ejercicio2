package es.per0na.vincle.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EventViewController {
    @GetMapping("/view-events")
    public String viewEvents() {
        return "view-events";
    }
}
