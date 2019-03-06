package com.example.demo.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Marque {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    /*
     * Id.
     */
    private Integer id;

    private String nom;

    @OneToMany(mappedBy = "marque", cascade = CascadeType.ALL)
    private Set<Figurine> figurines;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Set<Figurine> getFigurines() {
        return figurines;
    }

    public void setFigurines(Set<Figurine> figurines) {
        this.figurines = figurines;
    }

}
