package com.novahub.javatrain.javaspringbookmanagement.services;

import com.novahub.javatrain.javaspringbookmanagement.configurations.TokenProvider;
import com.novahub.javatrain.javaspringbookmanagement.controllers.dto.auth.SignInDTO;
import com.novahub.javatrain.javaspringbookmanagement.controllers.dto.auth.SignUpDTO;
import com.novahub.javatrain.javaspringbookmanagement.exceptions.UserExistedExeption;
import com.novahub.javatrain.javaspringbookmanagement.fakes.UserFaker;
import com.novahub.javatrain.javaspringbookmanagement.repositories.RoleRepository;
import com.novahub.javatrain.javaspringbookmanagement.repositories.UserRepository;
import com.novahub.javatrain.javaspringbookmanagement.repositories.entities.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class AuthServiceTest {
    
    @Mock
    UserRepository userRepository;
    
    @Mock
    RoleRepository roleRepository;
    
    @Mock
    TokenProvider jwtTokenUtil;
    
    @InjectMocks
    AuthService authService;
    
    @Test
    public void signUp_success() {
        User saveUser = UserFaker.createUser();
        saveUser.setId(1);
        when(roleRepository.findRoleByName("ROLE_USER")).thenReturn(UserFaker.mockRole);
        when(userRepository.save(any(User.class))).thenReturn(saveUser);
        when(userRepository.findUserByEmail(UserFaker.signUpDTO.getEmail())).thenReturn(null);
        assertEquals(authService.signUp(UserFaker.signUpDTO), saveUser);
        verify(userRepository).save(any(User.class));
    }
    
    @Test
    public void signUp_fail_existed_user() {
        SignUpDTO signUpDTO = UserFaker.signUpDTO;
        signUpDTO.setEmail("kien@gmail.com");
        when(userRepository.findUserByEmail("kien@gmail.com")).thenReturn(UserFaker.mockUserExisted);
        UserExistedExeption userExistedExeption = assertThrows(
                UserExistedExeption.class,
                () -> authService.signUp(signUpDTO));
    }
    
    @Test
    public void signIn_success() {
        SignInDTO signInDTO = UserFaker.signInDTO;
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(signInDTO.getEmail(), signInDTO.getPassword()));
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }
    
    
    
    
}
