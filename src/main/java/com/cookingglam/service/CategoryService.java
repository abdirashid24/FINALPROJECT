package com.cookingglam.service;

import com.cookingglam.entity.Category;
import com.cookingglam.repo.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Services for category add, remove, update
 */
@Service
public class CategoryService {
    @Autowired
    private CategoryRepo categoryRepo;

    public List<Category> getAll(){
        return categoryRepo.findAll();
    }
    public void addcat(Category category){categoryRepo.save(category); }
    public void deleteById(int id){
        categoryRepo.deleteById(id);
    }
    public Optional<Category> getById(int id){Optional<Category> category=categoryRepo.findById(id);return category; }
}
