package com.novahub.javatrain.javaspringbookmanagement.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.novahub.javatrain.javaspringbookmanagement.controllers.dto.book.CreateBookDTO;
import com.novahub.javatrain.javaspringbookmanagement.exceptions.BookNotFoundException;
import com.novahub.javatrain.javaspringbookmanagement.fakes.BookFaker;
import com.novahub.javatrain.javaspringbookmanagement.fakes.UserFaker;
import com.novahub.javatrain.javaspringbookmanagement.repositories.entities.Book;
import com.novahub.javatrain.javaspringbookmanagement.repositories.entities.User;
import com.novahub.javatrain.javaspringbookmanagement.services.BookService;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@WebAppConfiguration
@EnableAutoConfiguration(exclude = {org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration.class})
public class BookControllerTest {
    
    @MockBean
    private BookService bookService;
    
    @Autowired
    private MockMvc mockMvc;
    
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
        Book book = BookFaker.createBook();
        when(bookService.getBookById(1)).thenReturn(book);
        mockMvc.perform(MockMvcRequestBuilders.get("/books/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.id", Matchers.equalTo(1)));
    }
    
    @DisplayName("Find book by id and return status 400")
    @Test
    @WithMockUser(username = "test1@gmail.com", password = "123", roles = "USER")
    public void findBookByIdReturnStatus_400() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/books/{id}", "1+2")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isBadRequest());
    }
    
    @DisplayName("Find book by id and return status 404")
    @Test
    @WithMockUser(username = "test1@gmail.com", password = "123", roles = "USER")
    public void findBookByIdReturnStatus_404() throws Exception {
        when(bookService.getBookById(33)).thenThrow(new BookNotFoundException(33));
        mockMvc.perform(MockMvcRequestBuilders.get("/books/{id}", 33)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNotFound());
    }
    
    @DisplayName("create book and return status 201")
    @Test
    @WithMockUser(username = "test1@gmail.com", password = "123", roles = "USER")
    public void createBookReturnStatus_201() throws Exception {
        Book book = BookFaker.createBook();
        CreateBookDTO createBook = BookFaker.mockCreateBook;
        User user = UserFaker.createUser();
        when(bookService.createNewBook(any(CreateBookDTO.class))).thenReturn(book);
        mockMvc.perform(post("/books")
                        .content(asJsonString(createBook))
                        .contentType(MediaType.APPLICATION_JSON))
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.id", Matchers.equalTo(1)));
    }
    
    @DisplayName("edit book and return status 200")
    @Test
    @WithMockUser(username = "test1@gmail.com", password = "123", roles = "USER")
    public void editBookReturnStatus_200() throws Exception {
        assertDoesNotThrow(() -> bookService.editBook(BookFaker.editBookDTO, 1));
        mockMvc.perform(MockMvcRequestBuilders.put("/books/{id}", 1)
                        .content(asJsonString(BookFaker.editBookDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());
    }
    
    @DisplayName("edit book and return status 400")
    @Test
    @WithMockUser(username = "test1@gmail.com", password = "123", roles = "USER")
    public void editBookReturnStatus_400() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/books/{id}", "1+2")
                        .content(asJsonString(BookFaker.editBookDTO))
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
        mockMvc.perform(post("/books/status/{id}/enabled", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isForbidden());
    }
    
    @DisplayName("enable book with admin and return status 200")
    @Test
    @WithMockUser(username = "test1@gmail.com", password = "123", roles = "ADMIN")
    public void enableBookReturnStatus_200() throws Exception {
        assertDoesNotThrow(() -> bookService.enableBook(1, true));
        mockMvc.perform(post("/books/status/{id}/enabled", 1)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }
    
    @DisplayName("delete book with admin and return status 403")
    @Test
    @WithMockUser(username = "test1@gmail.com", password = "123", roles = "USER")
    public void deleteBookReturnStatus_403() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/books/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isForbidden());
    }
    
    @DisplayName("delete book with admin and return status 200")
    @Test
    @WithMockUser(username = "test1@gmail.com", password = "123", roles = "ADMIN")
    public void deleteBookReturnStatus_200() throws Exception {
        assertDoesNotThrow(() -> bookService.deleteBook(1));
        mockMvc.perform(MockMvcRequestBuilders.delete("/books/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }
    
    @DisplayName("enable book with admin and return status 403")
    @Test
    @WithMockUser(username = "test1@gmail.com", password = "123", roles = "USER")
    public void disableBookReturnStatus_403() throws Exception {
        mockMvc.perform(post("/books/status/{id}/disabled", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }
    
    @DisplayName("enable book with admin and return status 200")
    @Test
    @WithMockUser(username = "test1@gmail.com", password = "123", roles = "ADMIN")
    public void disableBookReturnStatus_200() throws Exception {
        assertDoesNotThrow(() -> bookService.enableBook(1, false));
        mockMvc.perform(post("/books/status/{id}/disabled", 1)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }
}