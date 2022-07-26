package com.novahub.javatrain.javaspringbookmanagement.fakes;

import com.novahub.javatrain.javaspringbookmanagement.controllers.dto.book.CreateBookDTO;
import com.novahub.javatrain.javaspringbookmanagement.controllers.dto.book.EditBookDTO;
import com.novahub.javatrain.javaspringbookmanagement.repositories.entities.Book;

public class BookFaker {
    public static Book createBook(){
        return Book.builder()
                .id(1)
                .title("test")
                .author("test")
                .description("test")
                .user(UserFaker.createUser())
                .build();
    }
    public static final CreateBookDTO mockCreateBook = CreateBookDTO.builder().title("test").author("test").description("test").build();
    public static final EditBookDTO editBookDTO = EditBookDTO.builder().title("test").author("test").description("test").build();
}
