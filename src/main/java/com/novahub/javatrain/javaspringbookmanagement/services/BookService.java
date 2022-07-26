package com.novahub.javatrain.javaspringbookmanagement.services;

import com.novahub.javatrain.javaspringbookmanagement.controllers.dto.book.BookStoreDTO;
import com.novahub.javatrain.javaspringbookmanagement.controllers.dto.book.CreateBookDTO;
import com.novahub.javatrain.javaspringbookmanagement.controllers.dto.book.EditBookDTO;
import com.novahub.javatrain.javaspringbookmanagement.exceptions.BookNotFoundException;
import com.novahub.javatrain.javaspringbookmanagement.repositories.BookRepository;
import com.novahub.javatrain.javaspringbookmanagement.repositories.entities.Book;
import com.novahub.javatrain.javaspringbookmanagement.repositories.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
@RequiredArgsConstructor
public class BookService {
    
    private final BookRepository booksRepository;
    
    private final AuthService authService;
    
    public Book getBookById(long id) {
        return getBookOrThrow(id);
    }
    
    public Book createNewBook(final CreateBookDTO requestCreateBookDto) {
        User user = this.authService.getMe();
        final Book book = Book.builder()
                .title(requestCreateBookDto.getTitle())
                .author(requestCreateBookDto.getAuthor())
                .description(requestCreateBookDto.getDescription())
                .enabled(false)
                .user(user)
                .build();
        return booksRepository.save(book);
    }
    
    public Book checkExistedBookById(User user, long id) {
        Book book = getBookOrThrow(id);
        if (book.getUser().getId() != user.getId()) {
            throw new BookNotFoundException(id);
        }
        return book;
    }
    
    public void editBook(final EditBookDTO editBookDto, long id) {
        User user = this.authService.getMe();
        Book existedBook = this.checkExistedBookById(user, id);
        existedBook.setTitle(editBookDto.getTitle());
        existedBook.setAuthor(editBookDto.getAuthor());
        existedBook.setDescription(editBookDto.getDescription());
        booksRepository.save(existedBook);
    }
    
    public void deleteBook(long id) {
        booksRepository.delete(getBookOrThrow(id));
    }
    
    public List<Book> getListBooks(String search, String orderBy) {
        return booksRepository.findAll(search, orderBy);
    }
    
    public void enableBook(long id, boolean isEnabled) {
        Book book = getBookOrThrow(id);
        book.setEnabled(isEnabled);
        booksRepository.save(book);
    }
    
    private Book getBookOrThrow(long id) {
        Optional<Book> book = booksRepository.findBookById(id);
        if (book.isEmpty()) {
            throw new BookNotFoundException(id);
        }
        return book.get();
    }

    public BookStoreDTO importBookFromStore(){
        WebClient client = WebClient.create("https://api.itbook.store/1.0/new");
        BookStoreDTO bookFromStore = client.get().retrieve().bodyToMono(BookStoreDTO.class).block();
        return bookFromStore;
    }
}
