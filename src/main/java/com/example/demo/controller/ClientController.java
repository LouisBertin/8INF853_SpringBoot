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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;


@Controller
public class ClientController {

    private FigurineRepository figurineRepository;

    private CategorieRepository categorieRepository;

    private MarqueRepository marqueRepository;

    private UserRepository userRepository;

    private ReservationRepository reservationRepository;

    public  ClientController(FigurineRepository figurineRepository, UserRepository userRepository, MarqueRepository marqueRepository, CategorieRepository categorieRepository, ReservationRepository reservationRepository){
        this.userRepository= userRepository;
        this.figurineRepository = figurineRepository;
        this.categorieRepository = categorieRepository;
        this.marqueRepository = marqueRepository;
        this.reservationRepository=reservationRepository;
    }

    @GetMapping(value="/figurines")
    public String displayFigurines(Model model){
        Iterable<Reservation> reservations = reservationRepository.findAll();
        for(Reservation reservation : reservations){
            if(reservation.getDate_expiration().before(new Date(Calendar.getInstance().getTime().getTime()))){
                reservation.getFigurine().setQuantite_stock(reservation.getFigurine().getQuantite_stock() + reservation.getQuantite());
                //reservationRepository.delete(reservation);
            }
        }
        model.addAttribute("figurines", figurineRepository.findAll());
        return "figurines";
    }

    @GetMapping(value="/figurines/reservation/{id}")
    public String reserverFigurine(Model model, @PathVariable("id") int id){
        Figurine figurine = figurineRepository.findById(id).get();
        model.addAttribute("figurine_book",figurine);
        return "reservation";
    }

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


    @GetMapping(value ="figurines/reservations")
    public String afficherFigurine(Model model){
        model.addAttribute("reservations", reservationRepository.findAll());
        return "reservations";
    }

    @GetMapping(value = "figurines/reservation/cancel/{id}")
    public String editReservation(@PathVariable("id")int id, Model model){
        model.addAttribute("reservation_", reservationRepository.findById(id).get());
        return "redirect:/figurines/reservations";
    }

    @PostMapping(value = "figurines/reservation/cancel/{id}")
    public String cancelReservationSubmit(@PathVariable("id") int id){
        Reservation reservation = reservationRepository.findById(id).get();
        reservation.getFigurine().setQuantite_magasin(reservation.getFigurine().getQuantite_magasin() + reservation.getQuantite());
        reservationRepository.deleteById(id);
        return "redirect:/figurines/reservations";
    }

}
