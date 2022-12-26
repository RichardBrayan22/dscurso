package com.example.dscommerce.services;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.dscommerce.dto.ProductDTO;
import com.example.dscommerce.entities.Product;
import com.example.dscommerce.repositories.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;
    
    /**
     * Metodo para buscar produto por id
     * @param id do produto que será localizado
     * @return o produto caso seja encontrado
     */
    @Transactional(readOnly = true)
    public ProductDTO findById(Long id){

        Optional<Product> product = repository.findById(id);
        ProductDTO dto = new ModelMapper().map(product.get(), ProductDTO.class);
        return dto;
    }


    @Transactional(readOnly = true)
    public Page<ProductDTO> findAll(Pageable pageable){

        Page<Product> products = repository.findAll(pageable);
        return products.map(product -> new ModelMapper().map(product, ProductDTO.class));
    }
    
    
    public ProductDTO insert(ProductDTO dto){
        
        // Removendo o id para conseguir fazer o cadastro
        dto.setId(null);

        //Criar um objeto de mapeamento
        ModelMapper mapper = new ModelMapper();

        //Converter o nosso produtoDTO em um Produto
        Product product = mapper.map(dto, Product.class);

        //Salvar o produto no banco
        product = repository.save(product);

        dto.setId(product.getId());

        //Retornar o ProdutoDTO atualizado
        return dto;

    }



}
