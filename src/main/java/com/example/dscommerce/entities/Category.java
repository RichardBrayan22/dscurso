package com.example.dscommerce.entities;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;

@Entity
@Table(name="tb_category")
public class Category {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;


    @ManyToMany(mappedBy = "categories")
    private Set<Product> products = new HashSet<>();

    //#region Contrutores
    public Category(){

    }

    public Category(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    //#endregion

    //#region Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Product> getProducts() {
        return products;
    }
    //#endregion
    

}
