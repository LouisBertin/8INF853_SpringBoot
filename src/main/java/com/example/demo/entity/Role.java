package com.example.demo.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * The type Role.
 */
@Entity
public class Role {
    @Id
    /*
     * Id.
     */
    private Integer id;
    /**
     * Name.
     */
    private String name;

    /**
     * Gets id.
     *
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }
}
