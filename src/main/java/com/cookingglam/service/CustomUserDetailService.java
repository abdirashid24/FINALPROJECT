package com.cookingglam.service;

import com.cookingglam.config.CustomUserDetail;
import com.cookingglam.entity.User;
import com.cookingglam.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * implementation of security by authenticating user details
 */
@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user=userRepo.findUserByEmail(email);
        user.orElseThrow(()-> new UsernameNotFoundException("User does not found"));
        return user.map(CustomUserDetail::new).get();
    }
}
