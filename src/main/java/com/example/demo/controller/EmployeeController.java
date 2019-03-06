package com.example.demo.controller;

import com.example.demo.entity.Categorie;
import com.example.demo.entity.Figurine;
import com.example.demo.entity.Image;
import com.example.demo.entity.Marque;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;

@Controller
public class EmployeeController {

    private FigurineRepository figurineRepository;

    private CategorieRepository categorieRepository;

    private MarqueRepository marqueRepository;

    private UserRepository userRepository;

    private ImageRepository imageRepository;

    private ClientController clientController;

    public static String uploadDirectory = System.getProperty("user.dir")+"\\src\\main\\resources\\static\\uploads";


    public  EmployeeController(FigurineRepository figurineRepository, ImageRepository imageRepository, UserRepository userRepository, MarqueRepository marqueRepository, CategorieRepository categorieRepository, ClientController clientController){
        this.userRepository= userRepository;
        this.figurineRepository = figurineRepository;
        this.categorieRepository = categorieRepository;
        this.marqueRepository = marqueRepository;
        this.clientController = clientController;
        this.imageRepository = imageRepository;
    }


    @GetMapping(value="/figurines/editFigurine/{id}")
    public String editFigurine(@PathVariable("id") int id, Model model){
        Figurine figurine = figurineRepository.findById(id).get();
        model.addAttribute("figurine_edit", figurine);
        model.addAttribute("categories", categorieRepository.findAll());
        model.addAttribute("marques", marqueRepository.findAll());
        return "editFigurine";
    }

    @PostMapping(value="/figurines/editFigurine/{id}")
    public String submitEditFigurine(@PathVariable("id") int id, @RequestParam("categorie_name") String categorie_name,
                                     @RequestParam("reference") String reference, @RequestParam("nom") String nom,
                                     @RequestParam("description") String description,@RequestParam("hauteur") float hauteur,
                                     @RequestParam("largeur") float largeur,@RequestParam("longueur") float longueur,
                                     @RequestParam("poids") float poids,@RequestParam("prix_ttc") float prix,
                                     @RequestParam("nb_exemplaires") int nb_exemplaires, @RequestParam("quantite_magasin") int quantite_magasin,
                                     @RequestParam("quantite_stock") int quantite_stock, @RequestParam("date_parution") Date date_parution,
                                     @RequestParam("marque_name") String marque_name){

        Figurine figurine = figurineRepository.findById(id).get();
        Iterable<Categorie> categories = categorieRepository.findAll();
        Iterable<Marque> marques = marqueRepository.findAll();

        figurine.setHauteur(hauteur);
        figurine.setDate_parution(date_parution);
        figurine.setDescription(description);
        figurine.setLargeur(largeur);
        figurine.setPoids(poids);
        figurine.setPrix_ttc(prix);
        figurine.setLongueur(longueur);
        figurine.setReference(reference);
        figurine.setNom(nom);
        figurine.setQuantite_magasin(quantite_magasin);
        figurine.setQuantite_stock(quantite_stock);
        figurine.setNb_exemplaires(nb_exemplaires);


        for(Categorie categorie : categories){
            if(categorie.getNom().equals(categorie_name)){
                figurine.setCategorie(categorie);
                break;
            }
        }

        for(Marque marque : marques){
            if(marque.getNom().equals(marque_name)){
                figurine.setMarque(marque);
                break;
            }
        }


        figurineRepository.save(figurine);

        return "redirect:/figurines";

    }

    @GetMapping(value="figurines/addFigurine")
    public String addFigurine(Model model){
        model.addAttribute("new_figurine", new Figurine());
        model.addAttribute("categories", categorieRepository.findAll());
        model.addAttribute("marques", marqueRepository.findAll());

        return "addFigurine";
    }

    @PostMapping(value="figurines/addFigurine")
    public String addFigurineSubmit(@ModelAttribute Figurine figurine, @ModelAttribute Image image, @RequestParam("categorie_nom") String categorie_nom,
                                    @RequestParam("marque_nom") String marque_nom, @RequestParam("UploadImage") MultipartFile UploadImage, RedirectAttributes redirectAttributes){

        Iterable<Categorie> categories = categorieRepository.findAll();
        Iterable<Marque> marques = marqueRepository.findAll();

        Iterable<Figurine> figurines = figurineRepository.findAll();


        String image_ [] = UploadImage.getOriginalFilename().split("\\.");


        image.setNom(image_[0]);
        image.setExtension("."+image_[1]);

        imageRepository.save(image);

        String image_id = image.getId() + "." + image_[1];

        StringBuilder filename = new StringBuilder();

        Path imagePath = Paths.get(uploadDirectory,image_id);
        filename.append(image_id);
        try{
            Files.write(imagePath, UploadImage.getBytes());
        }
        catch(Exception e){

        }

        figurine.setImage(image);

        for(Categorie categorie : categories){
            if(categorie.getNom().equals(categorie_nom)){
                figurine.setCategorie(categorie);
                break;
            }
        }

        for(Marque marque : marques) {
            if (marque.getNom().equals(marque_nom)) {
                figurine.setMarque(marque);
                break;
            }
        }

        figurineRepository.save(figurine);


            return "redirect:/figurines";
        }


    }