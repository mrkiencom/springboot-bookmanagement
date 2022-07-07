package com.novahub.javatrain.javaspringbookmanagement.mock;

import com.novahub.javatrain.javaspringbookmanagement.controllers.dto.book.CreateBookDTO;
import com.novahub.javatrain.javaspringbookmanagement.controllers.dto.book.EditBookDTO;
import com.novahub.javatrain.javaspringbookmanagement.repositories.entities.Book;
import com.novahub.javatrain.javaspringbookmanagement.repositories.entities.Role;
import com.novahub.javatrain.javaspringbookmanagement.repositories.entities.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class MockValue {
    public static final Role mockRole = Role.builder().id(1).name("ROLE_USER").build();
    public static final User mockUser = User.builder()
                                        .email("test@gmail.com")
                                        .password(new BCryptPasswordEncoder().encode("test"))
                                        .role(mockRole)
                                        .build();
    public static final Book mockBook = Book.builder().id(1).title("test").author("test").description("test").user(mockUser).build();
    public static final CreateBookDTO mockCreateBook = CreateBookDTO.builder().title("test").author("test").description("test").build();
    public static final EditBookDTO editBookDTO = EditBookDTO.builder().title("test").author("test").description("test").build();
    
}
