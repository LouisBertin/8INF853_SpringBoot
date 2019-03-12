package com.example.demo.controller;

import com.example.demo.entity.Figurine;
import com.example.demo.entity.Reservation;
import com.example.demo.entity.User;
import com.example.demo.repository.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Pattern;


/**
 * The type Client controller.
 */
@Controller
public class ClientController {

    private FigurineRepository figurineRepository;

    private ReservationRepository reservationRepository;

    private MarqueRepository marqueRepository;

    private CategorieRepository categorieRepository;

    /**
     * Instantiates a new Client controller.
     *
     * @param figurineRepository    the figurine repository
     * @param reservationRepository the reservation repository
     */
    public  ClientController(FigurineRepository figurineRepository, ReservationRepository reservationRepository, MarqueRepository marqueRepository, CategorieRepository categorieRepository){
        this.figurineRepository = figurineRepository;
        this.reservationRepository=reservationRepository;
        this.marqueRepository = marqueRepository;
        this.categorieRepository = categorieRepository;
    }

    /**
     * Display figurines string.
     *
     * @param model the model
     * @return the string
     */
    @GetMapping(value="/figurines")
    public String displayFigurines(Model model){
        Iterable<Reservation> reservations = reservationRepository.findAll();
        for(Reservation reservation : reservations){
            if(reservation.getDate_expiration().before(new Date(Calendar.getInstance().getTime().getTime()))){
                reservation.getFigurine().setQuantite_stock(reservation.getFigurine().getQuantite_stock() + reservation.getQuantite());
                reservationRepository.delete(reservation);
            }
        }
        model.addAttribute("figurines", figurineRepository.findAll());
        model.addAttribute("marques", marqueRepository.findAll());
        model.addAttribute("categories", categorieRepository.findAll());
        return "figurines";
    }

    /**
     * Reserver figurine string.
     *
     * @param model the model
     * @param id    the id
     * @return the string
     */
    @GetMapping(value="/figurines/reservation/{id}")
    public String reserverFigurine(Model model, @PathVariable("id") int id){
        Figurine figurine = figurineRepository.findById(id).get();
        model.addAttribute("figurine_book",figurine);
        return "reservation";
    }

    /**
     * Reserver figurine submit string.
     *
     * @param id       the id
     * @param quantite the quantite
     * @param model    the model
     * @return the string
     */
    @PostMapping(value="/figurines/reservation/{id}")
    public String reserverFigurineSubmit(@PathVariable("id") int id, @RequestParam("quantite") int quantite, Model model){
        Reservation reservation = new Reservation();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Figurine figurine = figurineRepository.findById(id).get();
        Calendar date_expiration_ = Calendar.getInstance();
        date_expiration_.add(Calendar.DATE,7);
        java.util.Date date = date_expiration_.getTime();
        reservation.setUser(user);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 7);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        String formater = format.format(cal.getTime());

        Date date1 = Date.valueOf(formater);
        reservation.setDate_expiration(date1);
        reservation.setAchete(false);
        reservation.setFigurine(figurine);
        reservation.setQuantite(quantite);
        reservation.setMontant(quantite*figurine.getPrix_ttc());
        if(quantite > figurine.getQuantite_stock()){
            quantite = quantite - figurine.getQuantite_stock();
            figurine.setQuantite_stock(0);
            figurine.setQuantite_magasin(figurine.getQuantite_magasin()-quantite);
        }
        else{
            figurine.setQuantite_stock(figurine.getQuantite_stock()-quantite);
        }
        reservationRepository.save(reservation);
        figurineRepository.save(figurine);
        return "redirect:/figurines";
    }


    /**
     * Afficher figurine string.
     *
     * @param model the model
     * @return the string
     */
    @GetMapping(value ="figurines/reservations")
    public String afficherFigurine(Model model){
        model.addAttribute("reservations", reservationRepository.findAll());
        return "reservations";
    }

    /**
     * Edit reservation string.
     *
     * @param id    the id
     * @param model the model
     * @return the string
     */
    @GetMapping(value = "figurines/reservation/cancel/{id}")
    public String editReservation(@PathVariable("id")int id, Model model){
        model.addAttribute("reservation_", reservationRepository.findById(id).get());
        return "redirect:/figurines/reservations";
    }

    /**
     * Cancel reservation submit string.
     *
     * @param id the id
     * @return the string
     */
    @PostMapping(value = "figurines/reservation/cancel/{id}")
    public String cancelReservationSubmit(@PathVariable("id") int id){
        Reservation reservation = reservationRepository.findById(id).get();
        reservation.getFigurine().setQuantite_magasin(reservation.getFigurine().getQuantite_magasin() + reservation.getQuantite());
        reservationRepository.deleteById(id);
        return "redirect:/figurines/reservations";
    }


    @GetMapping(value="figurines/recherche")
    public String displayRecherche(@RequestParam("recherche") String recherche,@RequestParam("marque_name") String marque_name,
                                   @RequestParam("categorie_name") String categorie_name, Model model){
        ArrayList<Figurine> figurineArrayList = new ArrayList<>();
        for(Figurine figurine : figurineRepository.findAll()){
            if(figurine.getNom().toLowerCase().contains(recherche.toLowerCase())){
                if(figurine.getCategorie().getNom().equals(categorie_name) || categorie_name.equals("Tous")){
                    if(figurine.getMarque().getNom().equals(marque_name) || marque_name.equals("Tous")){
                        figurineArrayList.add(figurine);
                    }
                }
            }
        }

        model.addAttribute("marques", marqueRepository.findAll());
        model.addAttribute("categories", categorieRepository.findAll());
        model.addAttribute("figurines_search", figurineArrayList);

        return "recherche";
    }

    @PostMapping(value="figurines/recherche")
    public String submitRecherche(){
        return "recherche";
    }
}
