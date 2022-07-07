package com.novahub.javatrain.javaspringbookmanagement.services;

import com.novahub.javatrain.javaspringbookmanagement.controllers.dto.book.CreateBookDTO;
import com.novahub.javatrain.javaspringbookmanagement.exceptions.BookNotFoundException;
import com.novahub.javatrain.javaspringbookmanagement.mock.MockValue;
import com.novahub.javatrain.javaspringbookmanagement.repositories.BookRepository;
import com.novahub.javatrain.javaspringbookmanagement.repositories.entities.Book;
import com.novahub.javatrain.javaspringbookmanagement.repositories.entities.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class BookServiceTest {
    @Mock
    BookRepository bookRepository;
    
    @InjectMocks
    BookService bookService;
    
    private User user;
    
    
    
    @Test
    public void createNewBook_Success(){
        CreateBookDTO createBookDTO = MockValue.mockCreateBook;
        final Book book = Book.builder()
                .title(createBookDTO.getTitle())
                .author(createBookDTO.getAuthor())
                .description(createBookDTO.getDescription())
                .enabled(false)
                .user(MockValue.mockUser)
                .build();
        
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        Book savedBook = bookService.createNewBook(MockValue.mockUser, createBookDTO);
        verify(bookRepository).save(any(Book.class));
        assertEquals(book, savedBook);
    }
    
    @Test
    public void getBookById_ThorwIsNotFound(){
        BookNotFoundException exception = assertThrows(BookNotFoundException.class,() ->bookService.getBookById(1L));
        assertEquals(exception.getMessage(),String.format("Book with id %s could not be found", 1L));
    }
    
    @Test
    public void getBookById_Success(){
        when(bookRepository.findBookById(1L)).thenReturn(Optional.of(MockValue.mockBook) );
        assertEquals(bookService.getBookById(1L),MockValue.mockBook);
    }
    
    @Test
    public void editBook_Success(){
        when(bookRepository.findBookById(1L)).thenReturn(Optional.of(MockValue.mockBook) );
        
        Book editBook = MockValue.mockBook;
        editBook.setTitle(MockValue.mockCreateBook.getTitle());
        editBook.setAuthor(MockValue.mockCreateBook.getAuthor());
        editBook.setDescription(MockValue.mockCreateBook.getDescription());
        
        when(bookRepository.save(any(Book.class))).thenReturn(editBook);
        assertDoesNotThrow(()->{
            bookService.editBook(MockValue.mockUser,MockValue.editBookDTO,1L);
        });
    }
    
    @Test
    public void editBook_ThrowNotFoundExeption(){
        BookNotFoundException exception = assertThrows(BookNotFoundException.class,() ->bookService.deleteBook(1L));
        assertEquals(exception.getMessage(),String.format("Book with id %s could not be found", 1L));
    }
    
    @Test
    public void enableBook_Success(){
        when(bookRepository.findBookById(1L)).thenReturn(Optional.of(MockValue.mockBook) );
        
        Book book = MockValue.mockBook;
        book.setEnabled(false);
        
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        assertDoesNotThrow(()->{
            bookService.enableBook(1L,false);
        });
    }
    @Test
    public void enableBook_ThrowNotFoundExeption(){
        BookNotFoundException exception = assertThrows(BookNotFoundException.class,() ->bookService.enableBook(1L,false));
        assertEquals(exception.getMessage(),String.format("Book with id %s could not be found", 1L));
    }
    
    @Test
    public void deleteBook_Success(){
        when(bookRepository.findBookById(1L)).thenReturn(Optional.of(MockValue.mockBook) );
        
        Book book = MockValue.mockBook;
        book.setEnabled(false);
        assertDoesNotThrow(()->{
            bookService.deleteBook(1L);
        });
    }
    
    @Test
    public void deleteBook_ThrowNotFoundExeption(){
        BookNotFoundException exception = assertThrows(BookNotFoundException.class,() ->bookService.deleteBook(1L));
        assertEquals(exception.getMessage(),String.format("Book with id %s could not be found", 1L));
    }
    
    @Test
    public void getListBook_Success_While_ExitedBook(){
        final String search = "test";
        final String orderBy = "test";
        List<Book> bookList = new ArrayList<>();
        bookList.add(MockValue.mockBook);
     
        when(bookRepository.findAll(search,orderBy)).thenReturn(bookList);
        assertEquals(bookService.getListBooks(search,orderBy),bookList);
    }
    
    @Test
    public void getListBook_Success_While_NotExistedBook(){
        final String search = "test";
        final String orderBy = "test";
        List<Book> bookListEmpty = new ArrayList<>();
        assertEquals(bookService.getListBooks(search,orderBy),bookListEmpty);
    }
    
}
