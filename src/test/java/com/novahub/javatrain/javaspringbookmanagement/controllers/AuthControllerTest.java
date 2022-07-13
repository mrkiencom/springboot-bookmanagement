package com.novahub.javatrain.javaspringbookmanagement.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.novahub.javatrain.javaspringbookmanagement.controllers.dto.auth.AuthToken;
import com.novahub.javatrain.javaspringbookmanagement.controllers.dto.auth.SignInDTO;
import com.novahub.javatrain.javaspringbookmanagement.controllers.dto.auth.SignUpDTO;
import com.novahub.javatrain.javaspringbookmanagement.exceptions.EmailInvalidException;
import com.novahub.javatrain.javaspringbookmanagement.exceptions.UserExistedExeption;
import com.novahub.javatrain.javaspringbookmanagement.fakes.AuthFaker;
import com.novahub.javatrain.javaspringbookmanagement.fakes.UserFaker;
import com.novahub.javatrain.javaspringbookmanagement.services.AuthService;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class AuthControllerTest {
    
    @MockBean
    private AuthService authService;
    
    @Autowired
    private MockMvc mockMvc;
    
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    
    @DisplayName("register account and return status 201")
    @Test
    public void registerAccountReturnStatus_201() throws Exception {
        when(authService.signUp(any(SignUpDTO.class))).thenReturn(UserFaker.createUser());
        mockMvc.perform(post("/auths/sign-up")
                        .content(asJsonString(UserFaker.signUpDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", Matchers.equalTo(UserFaker.createUser().getEmail())));
    }
    
    @DisplayName("login and return status 201")
    @Test
    public void LoginAccountReturnStatus_203() throws Exception {
        AuthToken responseLogin = AuthFaker.responseLogin;
        SignInDTO reqSignIn = UserFaker.signInDTO;
        when(authService.signIn(any(SignInDTO.class))).thenReturn(responseLogin);
        mockMvc.perform(post("/auths/sign-in")
                        .content(asJsonString(reqSignIn))
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.token", Matchers.equalTo(responseLogin.getToken())));
    }
    
    @DisplayName("register account and return status 201")
    @Test
    public void registerAccountReturnStatus_403() throws Exception {
        when(authService.signUp(any(SignUpDTO.class))).thenThrow(new UserExistedExeption(UserFaker.signUpDTO.getEmail()));
        mockMvc.perform(post("/auths/sign-up")
                        .content(asJsonString(UserFaker.signUpDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }
    
    @DisplayName("login and return status 201")
    @Test
    public void LoginAccountReturnStatus_403() throws Exception {
        AuthToken responseLogin = AuthFaker.responseLogin;
        SignInDTO reqSignIn = UserFaker.signInDTO;
        when(authService.signIn(any(SignInDTO.class))).thenThrow(new EmailInvalidException(UserFaker.signInDTO.getEmail()));
        mockMvc.perform(post("/auths/sign-in")
                        .content(asJsonString(reqSignIn))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }
    
    @DisplayName("login and return status 201")
    @Test
    public void LoginAccountReturnStatus_400() throws Exception {
        mockMvc.perform(post("/auths/sign-in")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
    
}
