package com.example.demo.controller;

import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    /**
     * UserRepository.
     */
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    public UserController(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    /**
     * Login string.
     *
     * @param model the model
     * @return the string
     */
    @GetMapping("/login")
    public String login(Model model) {

        return "user/login";
    }

    /**
     * Dashboard.
     *
     * @param model the model
     * @return the string
     */
    @GetMapping("/dashboard")
    public String dashboard(Model model) {

        return "dashboard";
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

        return "user/users";
    }

    /**
     * Add user form.
     *
     * @param model the model
     * @return the string
     */
    @GetMapping("/adduser")
    public String adduser(Model model) {
        model.addAttribute("user_", new User());
        Iterable<Role> roles = roleRepository.findAll();

        model.addAttribute("roles",roles);

        return "user/adduser";
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
