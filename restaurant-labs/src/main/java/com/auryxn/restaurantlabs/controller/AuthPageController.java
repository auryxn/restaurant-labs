package com.auryxn.restaurantlabs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthPageController {
    @GetMapping("/")
    public String home() { return "redirect:/restaurants"; }

    @GetMapping("/auth/login")
    public String login() { return "auth/login"; }
}
