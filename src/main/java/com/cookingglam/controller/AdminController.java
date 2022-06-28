package com.cookingglam.controller;

import com.cookingglam.dto.ProductDto;
import com.cookingglam.entity.Category;
import com.cookingglam.entity.Product;
import com.cookingglam.service.CategoryService;
import com.cookingglam.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Controller
/**
 * Controlling the urls accessed by admin
 */
public class AdminController {
    public static String uploadDir=System.getProperty("user.dir")+"/src/main/resources/productImages";
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;

    /**
     * admin home page
     */
    @GetMapping("/admin")
    public String adminHome(){
        return "adminHome";
    }

    /**
     * Admin viewing the categories
     */
    @GetMapping("/admin/categories")
    public String getCat(Model model){
        model.addAttribute("categories", categoryService.getAll());
        return "categories";
    }

    @GetMapping("/admin/categories/add")
    public String addCat(Model model){
        model.addAttribute("category", new Category());
        return "categoriesAdd";
    }

    /**
     * admin adding the category
     */
    @PostMapping("/admin/categories/add")
    public String addCat(@ModelAttribute("category") Category category){
        categoryService.addcat(category);
        return "redirect:/admin/categories";
    }

    /**
     * admin deleting the category by id
     */
    @GetMapping("/admin/categories/delete/{id}")
    public String deleteCate(@PathVariable int id){
        categoryService.deleteById( id);
        return "redirect:/admin/categories";
    }

    /**
     * Admin updating the category by id
     */
    @GetMapping("/admin/categories/update/{id}")
    public String updateCate(@PathVariable int id, Model model){
         Optional<Category> category = categoryService.getById(id);
         if (category.isPresent())
         {
             model.addAttribute("category", category.get());
             return "categoriesAdd";
         }
         else {
             return "404";
         }
        
    }

    /**
     * admin to delete the product by id
     */
    @GetMapping("/admin/product/delete/{id}")
    public String deletePro(@PathVariable int id){
        productService.deleteById( id);
        return "redirect:/admin/products";
    }

    /**
     * admin to view the products
     */
    @GetMapping("/admin/products")
    public String getPro(Model model){
        model.addAttribute("products", productService.getAll());
        return "products";
    }

    /**
     * admin to add the product
     */
    @GetMapping("/admin/products/add")
    public String addPro(Model model){
        model.addAttribute("productDTO", new ProductDto());
        model.addAttribute("categories", categoryService.getAll());
        return "productsAdd";
    }

    /**
     * admin to add the product
     */
    @PostMapping("/admin/products/add")
    public String addProduct(@ModelAttribute("productDTO") ProductDto productDto,
                             @RequestParam("productImage")MultipartFile multipartFile,
                             @RequestParam("imgName") String imgName) throws IOException{
        Product product=new Product();
        product.setId(productDto.getId());
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setWeight(productDto.getWeight());
        product.setDescription(productDto.getDescription());
        product.setCategory(categoryService.getById(productDto.getCategoryId()).get());
        String imageUUID;
        if(!multipartFile.isEmpty())
        {
            imageUUID=multipartFile.getOriginalFilename();
            Path fineNameAndPath= Paths.get(uploadDir, imageUUID);
            Files.write(fineNameAndPath,multipartFile.getBytes());
        }else {
            imageUUID=imgName;
        }
        product.setImageName(imageUUID);
        productService.addProduct(product);
        return "redirect:/admin/products";
    }

    /**
     * admin to update the product by id
     */

    @GetMapping("/admin/product/update/{id}")
    public String updateProd(@PathVariable int id, Model model){
        Product product= productService.getById(id).get();
        ProductDto productDTO=new ProductDto();
        productDTO.setId(product.getId());
        productDTO.setPrice(product.getPrice());
        productDTO.setDescription(product.getDescription());
        productDTO.setWeight(product.getWeight());
        productDTO.setName(product.getName());
        productDTO.setImageName(product.getImageName());
        productDTO.setCategoryId(product.getCategory().getId());
        model.addAttribute("categories",categoryService.getAll());
        model.addAttribute("productDTO", productDTO);
        return "productsAdd";
    }


}
