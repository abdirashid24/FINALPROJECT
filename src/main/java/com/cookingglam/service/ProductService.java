package com.cookingglam.service;

import com.cookingglam.entity.Product;
import com.cookingglam.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Services for product: add, remove, update
 */
@Service
public class ProductService {
    @Autowired
    private ProductRepo productRepo;

    public List<Product> getAll(){
        return productRepo.findAll();
    }
    public void addProduct(Product product){productRepo.save(product); }
    public void deleteById(int id){productRepo.deleteById(id); }
    public Optional<Product> getById(int id){return productRepo.findById(id); }
    public List<Product> getAllProductsByCategoryid(int id){
        return productRepo.findAllByCategory_Id(id);
    }
}
