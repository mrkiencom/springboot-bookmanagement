package com.novahub.javatrain.javaspringbookmanagement.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.novahub.javatrain.javaspringbookmanagement.controllers.dto.book.CreateBookDTO;
import com.novahub.javatrain.javaspringbookmanagement.controllers.dto.book.EditBookDTO;
import com.novahub.javatrain.javaspringbookmanagement.services.BookService;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class BookControllerTest {
    
    @MockBean
    private BookService bookService;
    
    @Autowired
    private MockMvc mockMvc;
  
    private CreateBookDTO createBookDTO;
    
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    
    @DisplayName("Find book by id and return status 200")
    @Test
    @WithMockUser(username = "test1@gmail.com", password = "123", roles = "USER")
    public void findBookByIdReturnStatus_200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/books/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    
    @DisplayName("Find book by id and return status 400")
    @Test
    @WithMockUser(username = "test1@gmail.com", password = "123", roles = "USER")
    public void findBookByIdReturnStatus_400() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/books/{id}", "1+2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
    
    
    @DisplayName("create book and return status 201")
    @Test
    @WithMockUser(username = "test1@gmail.com", password = "123", roles = "USER")
    public void createBookReturnStatus_201() throws Exception {
        createBookDTO = CreateBookDTO.builder().title("title-test").author("author-test").description("description-test").image("image-test").build();
        mockMvc.perform(MockMvcRequestBuilders.post("/books")
                        .content(asJsonString(createBookDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    
    @DisplayName("edit book and return status 200")
    @Test
    @WithMockUser(username = "test1@gmail.com", password = "123", roles = "USER")
    public void editBookReturnStatus_200() throws Exception {
        EditBookDTO editBookDTO = EditBookDTO.builder().title("title-test").author("author-test").description("description-test").image("image-test").build();
        mockMvc.perform(MockMvcRequestBuilders.put("/books/{id}", 1)
                        .content(asJsonString(editBookDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    
    @DisplayName("edit book and return status 400")
    @Test
    @WithMockUser(username = "test1@gmail.com", password = "123", roles = "USER")
    public void editBookReturnStatus_400() throws Exception {
        EditBookDTO editBookDTO = EditBookDTO.builder().title("title-test").author("author-test").description("description-test").image("image-test").build();
        mockMvc.perform(MockMvcRequestBuilders.put("/books/{id}", "1+2")
                        .content(asJsonString(editBookDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
    
    @DisplayName("find all book and return status 201")
    @Test
    @WithMockUser(username = "test1@gmail.com", password = "123", roles = "USER")
    public void listBookReturnStatus_200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/books")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    
    @DisplayName("enable book with admin and return status 403")
    @Test
    @WithMockUser(username = "test1@gmail.com", password = "123", roles = "USER")
    public void enableBookReturnStatus_403() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/books/status/{id}/enabled",1)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isForbidden());
        
    }
    @DisplayName("enable book with admin and return status 200")
    @Test
    @WithMockUser(username = "test1@gmail.com", password = "123", roles = "ADMIN")
    public void enableBookReturnStatus_200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/books/status/{id}/enabled",1)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }
    
    @DisplayName("delete book with admin and return status 403")
    @Test
    @WithMockUser(username = "test1@gmail.com", password = "123", roles = "USER")
    public void deleteBookReturnStatus_403() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/books/{id}",1)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isForbidden());
    }
    
    @DisplayName("delete book with admin and return status 200")
    @Test
    @WithMockUser(username = "test1@gmail.com", password = "123", roles = "ADMIN")
    public void deleteBookReturnStatus_200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/books/{id}",1)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }
}