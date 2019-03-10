package com.example.demo.controller;

import com.example.demo.entity.*;
import com.example.demo.repository.*;
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

    private ReservationRepository reservationRepository;

    public static String uploadDirectory = System.getProperty("user.dir")+"\\src\\main\\resources\\static\\uploads";


    public  EmployeeController(ReservationRepository reservationRepository,FigurineRepository figurineRepository, ImageRepository imageRepository, UserRepository userRepository, MarqueRepository marqueRepository, CategorieRepository categorieRepository, ClientController clientController){
        this.userRepository= userRepository;
        this.figurineRepository = figurineRepository;
        this.categorieRepository = categorieRepository;
        this.marqueRepository = marqueRepository;
        this.clientController = clientController;
        this.imageRepository = imageRepository;
        this.reservationRepository = reservationRepository;
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
                                     @RequestParam("marque_name") String marque_name, @RequestParam("UploadNewImage") MultipartFile UploadImage){

        Figurine figurine = figurineRepository.findById(id).get();
        Iterable<Categorie> categories = categorieRepository.findAll();
        Iterable<Marque> marques = marqueRepository.findAll();

        if(!UploadImage.isEmpty()){
            String image_ [] = UploadImage.getOriginalFilename().split("\\.");

            Image image = figurine.getImage();

            Path image_path = Paths.get(uploadDirectory,figurine.getId()+image.getExtension());

            if(!UploadImage.getContentType().equals("image/jpeg") && !UploadImage.getContentType().equals("image/png")){
                return "redirect:/figurines/addFigurine";
            }

            image.setNom(image_[0]);
            image.setExtension("."+image_[1]);

            imageRepository.save(image);

            String image_id = figurine.getId() + "." + image_[1];

            StringBuilder filename = new StringBuilder();

            Path new_imagePath = Paths.get(uploadDirectory,image_id);
            filename.append(image_id);

            try{
                Files.delete(image_path);
                Files.write(new_imagePath, UploadImage.getBytes());
            }
            catch(Exception e){

            }

            figurine.setImage(image);

        }

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
                                    @RequestParam("marque_nom") String marque_nom, @RequestParam("UploadImage") MultipartFile UploadImage, Model model){

        Iterable<Categorie> categories = categorieRepository.findAll();
        Iterable<Marque> marques = marqueRepository.findAll();

        Iterable<Figurine> figurines = figurineRepository.findAll();

        String image_ [] = UploadImage.getOriginalFilename().split("\\.");

        if(!UploadImage.getContentType().equals("image/jpeg") && !UploadImage.getContentType().equals("image/png")){
            return "redirect:/figurines/addFigurine";
        }

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

    @GetMapping(value="/figurines/categories/editCategorie/{id}")
    public String editCategorie(@PathVariable("id") int id, Model model){
        Categorie categorie = categorieRepository.findById(id).get();
        model.addAttribute("categorie_edit", categorie);
        return "editCategorie";
    }

    @PostMapping(value="/figurines/categories/editCategorie/{id}")
    public String editCategorieSubmit(@ModelAttribute Categorie categorie, @PathVariable("id") int id, Model model, @RequestParam("nom_categorie") String nom_categorie){
        categorie.setNom(nom_categorie);
        categorieRepository.save(categorie);
        return "redirect:/figurines/categories";
    }

    @GetMapping(value="figurines/categories/addCategorie")
    public String addCategorie(Model model){
        model.addAttribute("new_categorie", new Categorie());
        return "addCategorie";
    }

    @PostMapping(value="figurines/categories/addCategorie")
    public String addCategorieSubmit(Model model, @ModelAttribute Categorie categorie){
        categorieRepository.save(categorie);
        return "redirect:/figurines/categories";
    }

    @GetMapping(value="/figurines/marques/editMarque/{id}")
    public String editMarque(@PathVariable("id") int id, Model model){
        Marque marque = marqueRepository.findById(id).get();
        model.addAttribute("marque_edit", marque);
        return "editMarque";
    }

    @PostMapping(value="/figurines/marques/editMarque/{id}")
    public String editMarqueSubmit(@ModelAttribute Marque marque, @PathVariable("id") int id, Model model, @RequestParam("nom_marque") String nom_marque){
        marque.setNom(nom_marque);
        marqueRepository.save(marque);
        return "redirect:/figurines/marques";
    }

    @GetMapping(value="figurines/marques/addMarque")
    public String addMarque(Model model){
        model.addAttribute("new_marque", new Marque());
        return "addMarque";
    }

    @PostMapping(value="figurines/marques/addMarque")
    public String addMarqueSubmit(Model model, @ModelAttribute Marque marque){
        marqueRepository.save(marque);
        return "redirect:/figurines/marques";
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

    @GetMapping(value = "figurines/reservation/edit/{id}")
    public String editReservation(@PathVariable("id")int id, Model model){
        model.addAttribute("reservation_", reservationRepository.findById(id).get());
        return "redirect:/figurines/reservations";
    }

    @PostMapping(value="figurines/reservation/edit/{id}")
    public String  editReservationSubmit(@PathVariable("id") int id){
        Reservation reservation = reservationRepository.findById(id).get();
        reservation.setAchete(true);
        reservationRepository.save(reservation);
        return "redirect:/figurines/reservations";
    }


}