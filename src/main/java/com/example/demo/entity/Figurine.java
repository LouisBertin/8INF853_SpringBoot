package com.example.demo.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Figurine {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    /*
     * Id.
     */
    private Integer id;

    private String nom;

    private float prix_ttc;

    private Integer quantite_magasin;

    private Integer quantite_stock;

    private java.sql.Date date_parution;

    private Integer nb_exemplaires;

    private float poids;

    private float longueur;

    private float largeur;

    private float hauteur;

    private String reference;

    private String description;

    @ManyToOne
    @JoinColumn
    private Categorie categorie;

    @ManyToOne
    @JoinColumn
    private Marque marque;

    @ManyToOne
    @JoinColumn
    private Image image;

    @OneToMany
    private Set<Reservation> reservations;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {return nom;}

    public void setNom(String nom) {this.nom = nom; }

    public float getPrix_ttc() {
        return prix_ttc;
    }

    public void setPrix_ttc(float prix_ttc) {
        this.prix_ttc = prix_ttc;
    }

    public Integer getQuantite_magasin() {
        return quantite_magasin;
    }

    public void setQuantite_magasin(Integer quantite_magasin) {
        this.quantite_magasin = quantite_magasin;
    }

    public Integer getQuantite_stock() {
        return quantite_stock;
    }

    public void setQuantite_stock(Integer quantite_stock) {
        this.quantite_stock = quantite_stock;
    }

    public java.sql.Date getDate_parution() {
        return date_parution;
    }

    public void setDate_parution(java.sql.Date date_parution) {
        this.date_parution = date_parution;
    }

    public Integer getNb_exemplaires() {
        return nb_exemplaires;
    }

    public void setNb_exemplaires(Integer nb_exemplaires) {
        this.nb_exemplaires = nb_exemplaires;
    }

    public float getPoids() {
        return poids;
    }

    public void setPoids(float poids) {
        this.poids = poids;
    }

    public float getLongueur() {
        return longueur;
    }

    public void setLongueur(float longueur) {
        this.longueur = longueur;
    }

    public float getLargeur() {
        return largeur;
    }

    public void setLargeur(float largeur) {
        this.largeur = largeur;
    }

    public float getHauteur() {
        return hauteur;
    }

    public void setHauteur(float hauteur) {
        this.hauteur = hauteur;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public Marque getMarque() {
        return marque;
    }

    public void setMarque(Marque marque) {
        this.marque = marque;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Set<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(Set<Reservation> reservations) {
        this.reservations = reservations;
    }
}


