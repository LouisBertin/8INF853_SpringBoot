package com.example.demo.controller;

import com.example.demo.entity.*;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.FigurineRepository;
import com.example.demo.repository.CategorieRepository;
import com.example.demo.repository.MarqueRepository;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;



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
    public String userPage(Model model){
        model.addAttribute("figurines", figurineRepository.findAll());
        return "figurines";
    }

}
