package com.novahub.javatrain.javaspringbookmanagement.services;


import com.novahub.javatrain.javaspringbookmanagement.configurations.TokenProvider;
import com.novahub.javatrain.javaspringbookmanagement.controllers.dto.auth.AuthToken;
import com.novahub.javatrain.javaspringbookmanagement.controllers.dto.auth.RequestSignInDto;
import com.novahub.javatrain.javaspringbookmanagement.controllers.dto.auth.RequestSignUpDto;
import com.novahub.javatrain.javaspringbookmanagement.repositories.RoleRepository;
import com.novahub.javatrain.javaspringbookmanagement.repositories.UserRepository;
import com.novahub.javatrain.javaspringbookmanagement.repositories.entities.Role;
import com.novahub.javatrain.javaspringbookmanagement.repositories.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    
    @Autowired
    private TokenProvider jwtTokenUtil;
    
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
    
    public ResponseEntity<?> signIn(RequestSignInDto requestSignInDto) {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        requestSignInDto.getEmail(),
                        requestSignInDto.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtTokenUtil.generateToken(authentication,requestSignInDto.getEmail());
        return ResponseEntity.ok(new AuthToken(token));
    }
}
