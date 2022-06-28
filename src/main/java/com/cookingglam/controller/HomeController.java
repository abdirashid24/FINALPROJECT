package com.cookingglam.controller;

import com.cookingglam.global.GlobalData;
import com.cookingglam.service.CategoryService;
import com.cookingglam.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Home controllers
 */
@Controller
public class HomeController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;

    /**
     * user/ admin access home
     */
    @GetMapping({"/", "/home"})
    public String homePage(Model model){
        model.addAttribute("cartCount", GlobalData.cart.size());
        model.addAttribute("categories", categoryService.getAll());
        model.addAttribute("products", productService.getAll());
        model.addAttribute("cartCount", GlobalData.cart.size());

        return "index";
    }

    /**
     * about page
     */
    @GetMapping("/about")
    public String about(){
        return "about";
    }

    /**
     * contact page
     */
    @GetMapping("/contact")
    public String contact(){
        return "contact";
    }
    /**
     * access shop
     */
    @GetMapping("/shop")
    public String shop(Model model)
    {
        model.addAttribute("cartCount", GlobalData.cart.size());
        model.addAttribute("categories", categoryService.getAll());
        model.addAttribute("products", productService.getAll());
        return "shop";
    }

    /**
     * view products in shop by category id
     */
    @GetMapping("/shop/category/{id}")
    public String shopByCategoryId(Model model, @PathVariable int id)
    {
        model.addAttribute("categories", categoryService.getAll());
        model.addAttribute("products", productService.getAllProductsByCategoryid(id));
        model.addAttribute("cartCount", GlobalData.cart.size());
        return "shop";
    }

    /**
     * view shop by product id
     */
    @GetMapping("/shop/viewproduct/{id}")
    public String viewProduct(Model model, @PathVariable int id)
    {
        model.addAttribute("product",productService.getById(id).get());
        model.addAttribute("cartCount", GlobalData.cart.size());
        return "viewProduct";
    }

}
