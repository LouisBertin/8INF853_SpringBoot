package com.example.demo.controller;

import com.example.demo.entity.*;
import com.example.demo.repository.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


/**
 * The type Admin controller.
 */
@Controller
public class AdminController {

    private FigurineRepository figurineRepository;

    private EmployeeController employeeController;

    /**
     * Instantiates a new Admin controller.
     *
     * @param figurineRepository the figurine repository
     * @param employeeController the employee controller
     */
    public AdminController(FigurineRepository figurineRepository, EmployeeController employeeController){
        this.figurineRepository = figurineRepository;
        this.employeeController = employeeController;
    }

    /**
     * Delete figurine string.
     *
     * @param id    the id
     * @param model the model
     * @return the string
     */
    @GetMapping(value="/figurines/deleteFigurine/{id}")
    public String deleteFigurine(@PathVariable("id") int id,  Model model){
        Figurine figurine = figurineRepository.findById(id).get();
        model.addAttribute("figurine_delete",figurine);
        return "figurines/deleteFigurine";
    }

    /**
     * Delete figurine submit string.
     *
     * @param id    the id
     * @param model the model
     * @return the string
     */
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
        return "redirect:/figurines";
    }

}

