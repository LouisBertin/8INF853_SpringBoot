package com.example.demo.controller;

import com.example.demo.entity.*;
import com.example.demo.repository.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@Controller
public class AdminController {

    private FigurineRepository figurineRepository;

    private CategorieRepository categorieRepository;

    private MarqueRepository marqueRepository;

    private UserRepository userRepository;

    private ImageRepository imageRepository;

    private ClientController clientController;

    private EmployeeController employeeController;

    public  AdminController(ImageRepository imageRepository, FigurineRepository figurineRepository, UserRepository userRepository, MarqueRepository marqueRepository, CategorieRepository categorieRepository, ClientController clientController, EmployeeController employeeController){
        this.userRepository= userRepository;
        this.figurineRepository = figurineRepository;
        this.categorieRepository = categorieRepository;
        this.marqueRepository = marqueRepository;
        this.clientController = clientController;
        this.imageRepository = imageRepository;
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
        Figurine figurine = figurineRepository.findById(id).get();
        Image image = figurine.getImage();
        Path image_path = Paths.get(employeeController.uploadDirectory,figurine.getId()+image.getExtension());
        try{
            Files.delete(image_path);
        }
        catch(Exception e){

        }
        figurineRepository.deleteById(id);
        imageRepository.deleteById(id);
        return "redirect:/figurines";
    }

}

