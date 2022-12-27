package com.example.dscommerce.services;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.dscommerce.dto.ProductDTO;
import com.example.dscommerce.entities.Product;
import com.example.dscommerce.repositories.ProductRepository;
import com.example.dscommerce.services.exceptions.DatabaseException;
import com.example.dscommerce.services.exceptions.ResourceNotFoundException;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    /**
     * Metodo para buscar produto por id
     * 
     * @param id do produto que será localizado
     * @return o produto caso seja encontrado
     */
    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {

        Optional<Product> product = repository.findById(id);
        if (product.isEmpty()) {
            throw new ResourceNotFoundException("Recurso não encontrado");
        }
        ProductDTO dto = new ModelMapper().map(product.get(), ProductDTO.class);
        return dto;
    }

    /**
     * Metodo para buscar todos os produtos
     * 
     * @param pageable para paginar todos os produtos
     * @return lista paginada
     */
    @Transactional(readOnly = true)
    public Page<ProductDTO> findAll(Pageable pageable) {

        Page<Product> products = repository.findAll(pageable);
        return products.map(product -> new ModelMapper().map(product, ProductDTO.class));
    }

    /**
     * Metodo para inserir um produto no banco de dados
     * 
     * @param dto product
     * @return uma inserção do produto no banco
     */
    public ProductDTO insert(ProductDTO dto) {

        // Removendo o id para conseguir fazer o cadastro
        dto.setId(null);

        // Criar um objeto de mapeamento
        ModelMapper mapper = new ModelMapper();

        // Converter o nosso produtoDTO em um Produto
        Product product = mapper.map(dto, Product.class);

        // Salvar o produto no banco
        product = repository.save(product);

        dto.setId(product.getId());

        // Retornar o ProdutoDTO atualizado
        return dto;

    }

    /**
     * Metodo para atualizar o produto
     * 
     * @param id  do produto
     * @param dto product
     * @return o produto DTO atualizado
     */
    public ProductDTO update(Long id, ProductDTO dto) {
        dto.setId(id);
        ModelMapper mapper = new ModelMapper();
        Product product = mapper.map(dto, Product.class);
        Optional<Product> findProduct = repository.findById(product.getId());
        if (findProduct.isEmpty()) {
            throw new ResourceNotFoundException("Recurso nao encontrado");
        }
        repository.save(product);
        return dto;
    }

    /**
     * Metodo para deletar o produto
     * 
     * @param id do produto a ser deletado.
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Recurso nao encontrado");
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Falha de integridade referencial");
        }
    }
}
