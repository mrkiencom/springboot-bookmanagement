package com.novahub.javatrain.javaspringbookmanagement.repositories;

import com.novahub.javatrain.javaspringbookmanagement.repositories.entities.Book;
import com.novahub.javatrain.javaspringbookmanagement.repositories.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book,Long> {
    Optional<Book> findBookByIdAndUser(long id, User user);
    
    @Query(value = "SELECT * FROM Book WHERE title LIKE CONCAT('%',:search,'%') or author LIKE CONCAT('%',:search,'%')  ORDER BY :orderBy", nativeQuery = true)
    List<Book> findAll(@Param("search") String search, String orderBy);
    
    Optional<Book> findBookById(long id);
}
