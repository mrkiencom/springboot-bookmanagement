package com.novahub.javatrain.javaspringbookmanagement.services;


import com.novahub.javatrain.javaspringbookmanagement.configurations.TokenProvider;
import com.novahub.javatrain.javaspringbookmanagement.controllers.dto.auth.AuthToken;
import com.novahub.javatrain.javaspringbookmanagement.controllers.dto.auth.SignInDTO;
import com.novahub.javatrain.javaspringbookmanagement.controllers.dto.auth.SignUpDTO;
import com.novahub.javatrain.javaspringbookmanagement.exceptions.UserExistedExeption;
import com.novahub.javatrain.javaspringbookmanagement.repositories.RoleRepository;
import com.novahub.javatrain.javaspringbookmanagement.repositories.UserRepository;
import com.novahub.javatrain.javaspringbookmanagement.repositories.entities.Role;
import com.novahub.javatrain.javaspringbookmanagement.repositories.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
    
    public User signUp(SignUpDTO signUpDTO) {
        String email = signUpDTO.getEmail();
        User userExisted = userRepository.findUserByEmail(email);
        if(userExisted != null){
            throw new UserExistedExeption(email);
        }
        Role userRole = roleRepository.findRoleByName("ROLE_USER");
        User user = User.builder()
                .email( signUpDTO.getEmail())
                .firstName(signUpDTO.getFirstName())
                .lastName(signUpDTO.getLastName())
                .avatar(signUpDTO.getAvatar())
                .role(userRole)
                .password(new BCryptPasswordEncoder().encode(signUpDTO.getPassword()))
                .build();
        
        return userRepository.save(user);
    }
    
    public AuthToken signIn(final SignInDTO signInDTO) {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signInDTO.getEmail(),
                        signInDTO.getPassword()
                )
        );
        final String token = jwtTokenUtil.generateToken(authentication, signInDTO.getEmail());
        return new AuthToken(token);
    }
    
    public User getMe() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();
        return userRepository.findUserByEmail(email);
    }
}
