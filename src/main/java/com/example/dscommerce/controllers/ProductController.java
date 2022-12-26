package com.example.dscommerce.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dscommerce.dto.ProductDTO;
import com.example.dscommerce.services.ProductService;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping(value = "/{id}")
    public ProductDTO findById(@PathVariable Long id){
        
        return service.findById(id);
        
        //#region Comentarios
        //Forma mais didatica
        //ProductDTO dto = service.findById(id);
        //return dto;

        //Forma utilizado com o Optional no metodo.
        //Optional<ProductDTO> dto = service.findById(id);
        //return dto
        //#endregion
    }

    @GetMapping()
    public Page<ProductDTO> findAll(Pageable pageable){
        Page<ProductDTO> products = service.findAll(pageable);
        return products;
    }
    
}
