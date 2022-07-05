package com.novahub.javatrain.javaspringbookmanagement.services;

import com.novahub.javatrain.javaspringbookmanagement.repositories.BookRepository;
import com.novahub.javatrain.javaspringbookmanagement.repositories.entities.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public class AdminService {
    
    @Autowired
    BookRepository bookRepository;
    
    
    public void deleteBook(long id){
        Optional<Book>  book = bookRepository.findBookById(id);
        if(book.isEmpty()){
            //throw
        }
       bookRepository.delete(book.get());
    }
    
    public void enableBook(long id){
        Book  book = bookRepository.findBookById(id).get();
        book.setEnabled(true);
        bookRepository.save(book);
    }
}
