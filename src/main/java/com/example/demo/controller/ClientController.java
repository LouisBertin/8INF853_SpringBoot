package com.example.demo.controller;

import com.example.demo.repository.UserRepository;
import com.example.demo.repository.FigurineRepository;
import com.example.demo.repository.CategorieRepository;
import com.example.demo.repository.MarqueRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class ClientController {

    private FigurineRepository figurineRepository;

    private CategorieRepository categorieRepository;

    private MarqueRepository marqueRepository;

    private UserRepository userRepository;

    public  ClientController(FigurineRepository figurineRepository, UserRepository userRepository, MarqueRepository marqueRepository, CategorieRepository categorieRepository){
        this.userRepository= userRepository;
        this.figurineRepository = figurineRepository;
        this.categorieRepository = categorieRepository;
        this.marqueRepository = marqueRepository;
    }

    @GetMapping(value="/figurines")
    public String displayFigurines(Model model){
        model.addAttribute("figurines", figurineRepository.findAll());
        return "figurines";
    }

    @GetMapping(value="/figurines/categories")
    public String displayCategories(Model model){
        model.addAttribute("categories_", categorieRepository.findAll());
        return "categories";
    }

    @GetMapping(value="/figurines/marques")
    public String displayMarques(Model model){
        model.addAttribute("marques_", marqueRepository.findAll());
        return "marques";
    }

}
