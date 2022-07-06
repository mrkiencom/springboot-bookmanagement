package com.novahub.javatrain.javaspringbookmanagement.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.novahub.javatrain.javaspringbookmanagement.controllers.dto.book.CreateBookDTO;
import com.novahub.javatrain.javaspringbookmanagement.controllers.dto.book.EditBookDTO;
import com.novahub.javatrain.javaspringbookmanagement.repositories.entities.Book;
import com.novahub.javatrain.javaspringbookmanagement.repositories.entities.Role;
import com.novahub.javatrain.javaspringbookmanagement.repositories.entities.User;
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

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class BookControllerTest {
    
    @MockBean
    private BookService bookService;
    
    @Autowired
    private MockMvc mockMvc;
    private List<Book> books;
    private Book book;
    private User user;
    private Role userRole;
    private Role adminRole;
    private CreateBookDTO createBookDTO;
    private EditBookDTO editBookDTO;
    
    
    
    @DisplayName("Find book by id and return status 200")
    @Test
    @WithMockUser(username = "test1@gmail.com", password = "123", roles = "USER_ROLE")
    public void findBookByIdReturnStatus200() throws Exception {
        given(bookService.getBookById(1)).willReturn(book);
        mockMvc.perform(MockMvcRequestBuilders.get("/books/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    
    @DisplayName("create book and return status 201")
    @Test
    @WithMockUser(username = "test1@gmail.com", password = "123", roles = "USER_ROLE")
    public void createBookReturnStatus201() throws Exception {
        userRole = Role.builder().id(1).name("USER_ROLE").build();
        adminRole = Role.builder().id(1).name("ADMIN_ROLE").build();
        book = Book.builder().id(1).title("title-test").author("author-test").description("description-test").image("image-test").enabled(false).build();
        user = User.builder().email("test1@gmail.com").firstName("test").lastName("1").role(userRole).password("123").build();
        editBookDTO = EditBookDTO.builder().title("title-test").author("author-test").description("description-test").image("image-test").build();
        createBookDTO = CreateBookDTO.builder().title("title-test").author("author-test").description("description-test").image("image-test").build();
    
        given(bookService.createNewBook(user, createBookDTO)).willReturn(book);
        mockMvc.perform(post("/books")
                        .content(asJsonString(createBookDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isCreated());
    }

//    @DisplayName("edit book and return status 200")
//    @Test
//    @WithMockUser(username = "test1@gmail.com",password = "123",roles = "USER_ROLE")
//    public void editBookReturnStatus200() throws Exception {
//        given(bookService.editBook().willReturn(void)
//        mockMvc.perform(MockMvcRequestBuilders.put("/books/{id}", 1)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//    }
    
    @DisplayName("find all book and return status 201")
    @Test
    @WithMockUser(username = "test1@gmail.com", password = "123", roles = "USER_ROLE")
    public void ListBookReturnStatus200() throws Exception {
        given(bookService.getListBooks("", "")).willReturn(books);
        mockMvc.perform(MockMvcRequestBuilders.get("/books")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());
    }
    
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    
}
