package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * The type Welcome controller.
 */
@Controller
public class FrontController {

    /**
     * Main string.
     *
     * @param model the model
     * @return the string
     */
    @GetMapping("/")
    public String main(Model model) {
        model.addAttribute("message", "toto!");

        return "front/index";
    }

    /**
     * About string.
     *
     * @param model the model
     * @return the string
     */
    @GetMapping("/about")
    public String about(Model model) {
        return "front/about";
    }

}
