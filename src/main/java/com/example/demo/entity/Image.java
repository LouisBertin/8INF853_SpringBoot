package com.example.demo.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Image {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    /*
     * Id.
     */
    private Integer id;

    private String nom;

    private String extension;

    @OneToOne(mappedBy = "image")
    private Figurine figurine;


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

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

}

