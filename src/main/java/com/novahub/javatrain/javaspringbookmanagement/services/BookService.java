package com.novahub.javatrain.javaspringbookmanagement.services;

import com.novahub.javatrain.javaspringbookmanagement.controllers.dto.book.CreateBookDTO;
import com.novahub.javatrain.javaspringbookmanagement.controllers.dto.book.EditBookDTO;
import com.novahub.javatrain.javaspringbookmanagement.controllers.dto.book.EnableBookDTO;
import com.novahub.javatrain.javaspringbookmanagement.exceptions.BookNotFoundException;
import com.novahub.javatrain.javaspringbookmanagement.repositories.BookRepository;
import com.novahub.javatrain.javaspringbookmanagement.repositories.entities.Book;
import com.novahub.javatrain.javaspringbookmanagement.repositories.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class BookService {
    @Autowired
    private BookRepository booksRepository;
    
    public Book getBookById(User user, long id) {
        Optional<Book> book = booksRepository.findBookByIdAndUser(id, user);
        if (book.isEmpty()) {
            throw new BookNotFoundException(String.format("Book with id %s could not be found", id));
        } else {
            return book.get();
        }
    }
    
    public Book createNewBook(User user, final CreateBookDTO requestCreateBookDto) {
        final Book book = Book.builder()
                .title(requestCreateBookDto.getTitle())
                .author(requestCreateBookDto.getAuthor())
                .description(requestCreateBookDto.getDescription())
                .enabled(false).user(user)
                .build();
        return booksRepository.save(book);
    }
    
    public Book checkExistedBookById(User user, long id) {
        Optional<Book> bookOptional = booksRepository.findBookByIdAndUser(id, user);
        if(bookOptional.isEmpty()){
            throw new BookNotFoundException(String.format("Book with id %s could not be found", id));
        }else{
            return bookOptional.get();
        }
    }
    
    public void editBook(User user, EditBookDTO editBookDto, long id) {
        Book existedBook = this.checkExistedBookById(user, id);
        existedBook.setTitle(editBookDto.getTitle());
        existedBook.setAuthor(editBookDto.getAuthor());
        existedBook.setDescription(editBookDto.getAuthor());
        existedBook.setEnabled(editBookDto.isEnabled());
        booksRepository.save(existedBook);
    }
    
    
    public void deleteBook(long id) {
        Optional<Book> book = booksRepository.findBookById(id);
        if(book.isEmpty()){
            throw new BookNotFoundException(String.format("Book with id %s could not be found", id));
        }else{
            booksRepository.delete(book.get());
        }
    }
    
    public List<Book> getListBooks(String search, String orderBy) {
        return booksRepository.findAll(search, orderBy);
    }
    
    
    public void enableBook(long id, EnableBookDTO enableBookDTO) {
        Optional<Book> book = booksRepository.findBookById(id);
        if (book.isEmpty()) {
            throw new BookNotFoundException(String.format("Book with id %s could not be found", id));
        }
        book.get().setEnabled(enableBookDTO.isEnable());
        booksRepository.save(book.get());
    }
}
