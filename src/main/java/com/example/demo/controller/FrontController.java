package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * The type Welcome controller.
 */
@Controller
public class FrontController {
    /**
     * UserRepository.
     */
    private UserRepository userRepository;

    public FrontController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Main string.
     *
     * @param model the model
     * @return the string
     */
    @GetMapping("/")
    public String main(Model model) {
        model.addAttribute("message", "toto!");

        return "index";
    }

    /**
     * Display all users.
     */
    @GetMapping("/users")
    public String users(Model model) {
        Iterable<User> users = userRepository.findAll();
        model.addAttribute("users", users);

        return "users";
    }

    /**
     * Add user form.
     *
     * @return the string
     */
    @GetMapping("/adduser")
    public String hello(Model model) {
        model.addAttribute("user", new User());

        return "adduser";
    }

    /**
     * Test to get url param
     */
    @RequestMapping(value="/test/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String test(@PathVariable("id") int id) {
        System.out.println(id);

        return "Test";
    }

    /**
     *
     * Save user.
     */
    @PostMapping("/adduser")
    @ResponseBody
    public String greetingSubmit(@ModelAttribute User user) {
        if (!user.getName().isEmpty() && !user.getEmail().isEmpty()) {
            userRepository.save(user);

            return "Name : " + user.getName();
        }

        return "Result";
    }

}
