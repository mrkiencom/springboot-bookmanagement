package com.novahub.javatrain.javaspringbookmanagement.services;


import com.novahub.javatrain.javaspringbookmanagement.controllers.dto.auth.RequestSignUpDto;
import com.novahub.javatrain.javaspringbookmanagement.repositories.RoleRepository;
import com.novahub.javatrain.javaspringbookmanagement.repositories.UserRepository;
import com.novahub.javatrain.javaspringbookmanagement.repositories.entities.Role;
import com.novahub.javatrain.javaspringbookmanagement.repositories.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Repository
@Transactional
@Configuration
@Service
public class AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private RoleRepository roleRepository;
    
    public User signUp(RequestSignUpDto requestSignUpDto) {
        Role userRole = roleRepository.findRoleByName("ROLE_USER");
        User user = User.builder()
                .email( requestSignUpDto.getEmail())
                .firstName(requestSignUpDto.getFirstName())
                .lastName(requestSignUpDto.getLastName())
                .avatar(requestSignUpDto.getAvatar())
                .role(userRole)
                .password(new BCryptPasswordEncoder().encode(requestSignUpDto.getPassword()))
                .build();
        return userRepository.save(user);
    }
}
