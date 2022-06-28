package com.cookingglam.controller;

import com.cookingglam.entity.Role;
import com.cookingglam.entity.User;
import com.cookingglam.global.GlobalData;
import com.cookingglam.repo.RoleRepo;
import com.cookingglam.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.PostConstruct;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Login urls controlled here
 */
@Controller
public class LoginController {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RoleRepo roleRepo;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * when login url is clicked
     */
    @GetMapping("/login")
    public String loginPage(){
        GlobalData.cart.clear();
        return "login";
    }

    /**
     * registering the user
     */
    @GetMapping("/register")
    public String registerPage(){
        return "register";
    }

    /**
     * registering the user
     */
    @PostMapping("/register")
    public String Register(@ModelAttribute("user") User user, HttpServletRequest request) throws ServletException {
        String password = user.getPassword();
        user.setPassword(bCryptPasswordEncoder.encode(password));
        List<Role> roles= new ArrayList<>();
        roles.add(roleRepo.findById(2).get());
        user.setRoles(roles);
        userRepo.save(user);
        request.login(user.getEmail(), password);
        return "redirect:/";
    }

    /**
     * this is for creating the admin and then having admin role at default(Super User)
     */
    @PostConstruct
    public void AdminUser(){

       List<User> users=userRepo.findAll();
        List<Role> role= roleRepo.findAll();
        if (role.isEmpty()){

            Role role1 = new Role();
            role1.setId(1);
            role1.setName("ROLE_ADMIN");

            Role role2 = new Role();
            role2.setId(2);
            role2.setName("ROLE_USER");
            roleRepo.save(role1);
            roleRepo.save(role2);
        }
        if (users.isEmpty())
        {
            User user= new User();
            user.setFirstName("abdirashid");
            user.setLastName("hassan");
            user.setEmail("admin@gmail.com");
            user.setPassword(bCryptPasswordEncoder.encode("admin"));
            List<Role> roles= new ArrayList<>();
            roles.add(roleRepo.findById(1).get());
            user.setRoles(roles);
            userRepo.save(user);
        }

    }
}
