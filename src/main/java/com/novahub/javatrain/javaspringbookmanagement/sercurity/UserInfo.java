package com.novahub.javatrain.javaspringbookmanagement.sercurity;

import com.novahub.javatrain.javaspringbookmanagement.repositories.UserRepository;
import com.novahub.javatrain.javaspringbookmanagement.repositories.entities.User;
import com.novahub.javatrain.javaspringbookmanagement.services.UserInfoDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public class UserInfo {
    
    
    @Autowired
    private UserRepository userRepository;
    
    public User getInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserInfoDetailsImpl userDetails = (UserInfoDetailsImpl) authentication.getPrincipal();
        String email = userDetails.getEmail();
        return userRepository.findByEmail(email);
    }
}
