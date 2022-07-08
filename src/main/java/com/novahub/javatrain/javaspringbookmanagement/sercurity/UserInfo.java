package com.novahub.javatrain.javaspringbookmanagement.sercurity;

import com.novahub.javatrain.javaspringbookmanagement.repositories.UserRepository;
import com.novahub.javatrain.javaspringbookmanagement.repositories.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public class UserInfo {
    
    
    @Autowired
    private UserRepository userRepository;
    
    public User getMe() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();
        return userRepository.findUserByEmail(email);
    }
}
