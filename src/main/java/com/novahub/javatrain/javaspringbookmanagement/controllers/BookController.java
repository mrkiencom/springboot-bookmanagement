package com.novahub.javatrain.javaspringbookmanagement.controllers;

import com.novahub.javatrain.javaspringbookmanagement.controllers.dto.book.CreateBookDTO;
import com.novahub.javatrain.javaspringbookmanagement.controllers.dto.book.EditBookDTO;
import com.novahub.javatrain.javaspringbookmanagement.repositories.BookRepository;
import com.novahub.javatrain.javaspringbookmanagement.repositories.entities.Book;
import com.novahub.javatrain.javaspringbookmanagement.sercurity.UserInfo;
import com.novahub.javatrain.javaspringbookmanagement.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    
    @Autowired
    private BookService booksServices;
    @Autowired
    private UserInfo userInfo;
    
    @Autowired
    private BookRepository bookRepository;
    
    @GetMapping("{id}")
    public Book getBookById(@PathVariable long id) {
        return booksServices.getBookById(id);
    }
    
    @PostMapping
    public Book createNewBook(@Valid @RequestBody CreateBookDTO requestCreateBookDto) {
        return booksServices.createNewBook(userInfo.getInfo(), requestCreateBookDto);
    }
    
    @PutMapping(value = "{id}")
    public void editBook(@Valid @RequestBody EditBookDTO editBookDto, @PathVariable long id) {
        booksServices.editBook(userInfo.getInfo(), editBookDto, id);
    }
    
    @GetMapping
    public List<Book> listBooks(@RequestParam(required = false, name = "search") String search, @RequestParam(required = false, name = "orderBy") String orderBy) {
        return booksServices.getListBooks(search, orderBy);
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteBook(@PathVariable long id) {
        booksServices.deleteBook(id);
    }
    
    @PostMapping("/status/{id}/enable")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void enableBook(@PathVariable long id) {
        booksServices.enableBook(id, true);
    }
    
    @PostMapping("/status/{id}/disabled")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void disableBook(@PathVariable long id) {
        booksServices.enableBook(id, false);
    }
}
