package com.novahub.javatrain.javaspringbookmanagement.controllers;

import com.novahub.javatrain.javaspringbookmanagement.configurations.TokenProvider;
import com.novahub.javatrain.javaspringbookmanagement.controllers.dto.auth.AuthToken;
import com.novahub.javatrain.javaspringbookmanagement.controllers.dto.auth.SignInDTO;
import com.novahub.javatrain.javaspringbookmanagement.controllers.dto.auth.SignUpDTO;
import com.novahub.javatrain.javaspringbookmanagement.repositories.entities.User;
import com.novahub.javatrain.javaspringbookmanagement.sercurity.UserInfo;
import com.novahub.javatrain.javaspringbookmanagement.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auths")
public class AuthController {
    
    @Autowired
    AuthenticationManager authenticationManager;
    
    @Autowired
    private TokenProvider jwtTokenUtil;
    @Autowired
    private AuthService authService;
    
    @Autowired
    private UserInfo userInfo;
    
    @PostMapping("/sign-up")
    private User signUp(@Valid @RequestBody SignUpDTO signUpDTO) {
        return authService.signUp(signUpDTO);
    }
    
    @PostMapping("/sign-in")
    public AuthToken login(@Valid @RequestBody SignInDTO signInDTO) {
        return authService.signIn(signInDTO);
    }
    
    @GetMapping("/getme" )
    private User getInfo( ) {
        return userInfo.getMe();
    }
}
