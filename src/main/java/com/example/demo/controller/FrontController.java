package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.entity.Role;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private RoleRepository roleRepository;

    /**
     * Instantiates a new Front controller.
     *
     * @param userRepository the user repository
     */
    public FrontController(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;

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
     * Hello string.
     *
     * @param model the model
     * @return the string
     */
    @GetMapping("/dashboard")
    public String hello(Model model) {

        return "dashboard";
    }

    /**
     * Login string.
     *
     * @param model the model
     * @return the string
     */
    @GetMapping("/login")
    public String login(Model model) {

        return "login";
    }

    /**
     * Display all users.
     *
     * @param model the model
     * @return the string
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
     * @param model the model
     * @return the string
     */
    @GetMapping("/adduser")
    public String adduser(Model model) {
        model.addAttribute("user", new User());
        Iterable<Role> roles = roleRepository.findAll();

        model.addAttribute("roles",roles);

        return "adduser";
    }

    /**
     * Test to get url param
     *
     * @param id the id
     * @return the string
     */
    @RequestMapping(value="/test/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String test(@PathVariable("id") int id) {
        System.out.println(id);

        return "Test";
    }

    /**
     * Save user.
     *
     * @param user the user
     * @return the string
     */
    @PostMapping("/adduser")
    @ResponseBody
    public String greetingSubmit(@ModelAttribute User user, @RequestParam("role_name") String role1) {
        if (!user.getName().isEmpty() && !user.getEmail().isEmpty()) {
            for(Role role : roleRepository.findAll()){
                if(role.getName().equals(role1)){
                    user.setRole(role);
                    break;
                }
            }
            userRepository.save(user);
            return "Name : " + user.getName();
        }

        return "Result";
    }

}
