package com.misael.curso.springboot.app.springboot_crud.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.misael.curso.springboot.app.springboot_crud.entities.Product;
import com.misael.curso.springboot.app.springboot_crud.repositories.ProductRepository;

@Service
public class ProductServiceImplementation implements ProductService{

    @Autowired
    private ProductRepository productRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Product> findAll() {
        return (List<Product>) productRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    @Transactional
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Transactional
    @Override
    public Optional<Product> update(Long id, Product product) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if(optionalProduct.isPresent()) {
            Product productDB = optionalProduct.orElseThrow();
            
            productDB.setName(product.getName());
            productDB.setDescription(product.getDescription());
            productDB.setPrice(product.getPrice());
            productDB.setSku(product.getSku());
            return Optional.of(productRepository.save(productDB));
        }
        return optionalProduct;                
    }

    @Transactional
    @Override
    public Optional<Product> delete(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        optionalProduct.ifPresent(productDB -> {
            productRepository.delete(productDB);
        });
        return optionalProduct;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsBySku(String sku) {
        return productRepository.existsBySku(sku);
    }

}
