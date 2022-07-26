package com.novahub.javatrain.javaspringbookmanagement.controllers;

import com.novahub.javatrain.javaspringbookmanagement.controllers.dto.book.BookStoreDTO;
import com.novahub.javatrain.javaspringbookmanagement.controllers.dto.book.CreateBookDTO;
import com.novahub.javatrain.javaspringbookmanagement.controllers.dto.book.EditBookDTO;
import com.novahub.javatrain.javaspringbookmanagement.repositories.BookRepository;
import com.novahub.javatrain.javaspringbookmanagement.repositories.entities.Book;
import com.novahub.javatrain.javaspringbookmanagement.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {
    
    private final BookService booksServices;
    
    private final BookRepository bookRepository;
    
    @GetMapping("{id}")
    @ResponseBody
    public Book getBookById(@PathVariable long id) {
        return booksServices.getBookById(id);
    }
    
    @PostMapping
    public Book createNew(@Valid @RequestBody CreateBookDTO requestCreateBookDto) {
        
        return booksServices.createNewBook(requestCreateBookDto);
    }
    
    @PutMapping(value = "{id}")
    public void editBook(@Valid @RequestBody EditBookDTO editBookDto, @PathVariable long id) {
        booksServices.editBook( editBookDto, id);
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
    
    @PostMapping("/status/{id}/enabled")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void enableBook(@PathVariable long id) {
        booksServices.enableBook(id, true);
    }
    
    @PostMapping("/status/{id}/disabled")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void disableBook(@PathVariable long id) {
        booksServices.enableBook(id, false);
    }

    @GetMapping("/test-get")
    public void test() throws IOException {
        booksServices.getBook();
    }
}
