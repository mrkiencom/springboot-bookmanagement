package com.novahub.javatrain.javaspringbookmanagement.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.novahub.javatrain.javaspringbookmanagement.controllers.dto.auth.RequestSignInDto;
import com.novahub.javatrain.javaspringbookmanagement.controllers.dto.auth.RequestSignUpDto;
import com.novahub.javatrain.javaspringbookmanagement.services.AuthService;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

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
        RequestSignUpDto requestSignUpDto = RequestSignUpDto.builder()
                                            .email("test@gmail.com")
                                            .password("test")
                                            .firstName("test")
                                            .lastName("test")
                                            .build();
        mockMvc.perform(MockMvcRequestBuilders.post("/auths/sign-up")
                        .content(asJsonString(requestSignUpDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    
    @DisplayName("login and return status 201")
    @Test
    public void LoginAccountReturnStatus_203() throws Exception {
        RequestSignInDto requestSignInDto = RequestSignInDto.builder()
                                            .email("test@gmail.com")
                                            .password("test")
                                            .build();
        mockMvc.perform(MockMvcRequestBuilders.post("/auths/sign-up")
                        .content(asJsonString(requestSignInDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    
    
}
