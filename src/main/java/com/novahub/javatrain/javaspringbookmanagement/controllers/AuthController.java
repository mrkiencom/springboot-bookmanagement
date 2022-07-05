package com.novahub.javatrain.javaspringbookmanagement.controllers;

import com.novahub.javatrain.javaspringbookmanagement.configurations.TokenProvider;
import com.novahub.javatrain.javaspringbookmanagement.controllers.dto.auth.AuthToken;
import com.novahub.javatrain.javaspringbookmanagement.controllers.dto.auth.RequestSignInDto;
import com.novahub.javatrain.javaspringbookmanagement.controllers.dto.auth.RequestSignUpDto;
import com.novahub.javatrain.javaspringbookmanagement.repositories.entities.User;
import com.novahub.javatrain.javaspringbookmanagement.sercurity.UserInfo;
import com.novahub.javatrain.javaspringbookmanagement.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private User signUp(@Valid @RequestBody RequestSignUpDto requestSignUpDto) {
        return authService.signUp(requestSignUpDto);
    }
    
    @PostMapping("/sign-in")
    public ResponseEntity<?> login(@Valid @RequestBody RequestSignInDto requestSignInDto) {
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
    
    @GetMapping("/getme" )
    private User getInfo( ) {
        return userInfo.getInfo();
    }
}
