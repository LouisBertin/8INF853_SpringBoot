package com.example.demo.controller;

import com.example.demo.entity.*;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.FigurineRepository;
import com.example.demo.repository.CategorieRepository;
import com.example.demo.repository.MarqueRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;



@Controller
public class AdminController {

    private FigurineRepository figurineRepository;

    private CategorieRepository categorieRepository;

    private MarqueRepository marqueRepository;

    private UserRepository userRepository;

    private ClientController clientController;

    private EmployeeController employeeController;

    public  AdminController(FigurineRepository figurineRepository, UserRepository userRepository, MarqueRepository marqueRepository, CategorieRepository categorieRepository, ClientController clientController, EmployeeController employeeController){
        this.userRepository= userRepository;
        this.figurineRepository = figurineRepository;
        this.categorieRepository = categorieRepository;
        this.marqueRepository = marqueRepository;
        this.clientController = clientController;
        this.employeeController = employeeController;
    }

    @GetMapping(value="/figurines/deleteFigurine/{id}")
    public String deleteFigurine(@PathVariable("id") int id,  Model model){
        Figurine figurine = figurineRepository.findById(id).get();
        model.addAttribute("figurine_delete",figurine);
        return "deleteFigurine";
    }

    @RequestMapping(value="/figurines/deleteFigurine/{id}")
    public String deleteFigurineSubmit(@PathVariable("id") int id,  Model model) {
        figurineRepository.deleteById(id);
        return "redirect:/figurines";
    }

}

