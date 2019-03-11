package com.example.demo.controller;

import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String dashboard(Model model,  @ModelAttribute("successRegister") final String some) {

        return "dashboard";
    }

    /**
     * Add user form.
     *
     * @param model the model
     * @return the string
     */
    @GetMapping("/register")
    public String adduser(Model model) {
        model.addAttribute("user_", new User());
        Iterable<Role> roles = roleRepository.findAll();

        model.addAttribute("roles",roles);

        return "user/register";
    }

    /**
     * Save user.
     *
     * @param user the user
     * @return the string
     */
    @PostMapping("/register")
    public String greetingSubmit(@ModelAttribute User user,
                                 @RequestParam("role_name") String role1,
                                 RedirectAttributes rm) {
        if (!user.getName().isEmpty() && !user.getEmail().isEmpty()) {
            for(Role role : roleRepository.findAll()){
                if(role.getName().equals(role1)){
                    user.setRole(role);
                    break;
                }
            }
            userRepository.save(user);
            rm.addAttribute("successRegister", "Account created!");

            return "redirect:/dashboard";
        }

        return "Result";
    }

}
