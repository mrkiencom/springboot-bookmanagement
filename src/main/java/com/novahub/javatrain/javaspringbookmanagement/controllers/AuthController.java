package com.novahub.javatrain.javaspringbookmanagement.controllers;

import com.novahub.javatrain.javaspringbookmanagement.controllers.dto.auth.AuthToken;
import com.novahub.javatrain.javaspringbookmanagement.controllers.dto.auth.SignInDTO;
import com.novahub.javatrain.javaspringbookmanagement.controllers.dto.auth.SignUpDTO;
import com.novahub.javatrain.javaspringbookmanagement.repositories.entities.User;
import com.novahub.javatrain.javaspringbookmanagement.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auths")
public class AuthController {
    
    private final AuthenticationManager authenticationManager;
    
    private final AuthService authService;
    
    @PostMapping("/sign-up")
    private User signUp(@Valid @RequestBody SignUpDTO signUpDTO) {
        return authService.signUp(signUpDTO);
    }
    
    @PostMapping("/sign-in")
    public AuthToken login(@Valid @RequestBody SignInDTO signInDTO) {
        return authService.signIn(signInDTO);
    }
    
}
