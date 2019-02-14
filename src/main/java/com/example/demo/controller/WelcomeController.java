package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {

    @GetMapping("/")
    public String main(Model model) {
        model.addAttribute("message", "toto!");

        return "index";
    }

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

}
