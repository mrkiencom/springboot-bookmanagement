package com.novahub.javatrain.javaspringbookmanagement.services;

import com.novahub.javatrain.javaspringbookmanagement.constants.Constants;
import com.novahub.javatrain.javaspringbookmanagement.controllers.dto.book.BookFromStoreDTO;
import com.novahub.javatrain.javaspringbookmanagement.controllers.dto.book.BookStoreDTO;
import com.novahub.javatrain.javaspringbookmanagement.controllers.dto.book.CreateBookDTO;
import com.novahub.javatrain.javaspringbookmanagement.controllers.dto.book.EditBookDTO;
import com.novahub.javatrain.javaspringbookmanagement.exceptions.BookNotFoundException;
import com.novahub.javatrain.javaspringbookmanagement.repositories.BookRepository;
import com.novahub.javatrain.javaspringbookmanagement.repositories.entities.Book;
import com.novahub.javatrain.javaspringbookmanagement.repositories.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

    public BookStoreDTO getBookFromStore() {
        WebClient client = WebClient.create(Constants.BOOKSTORE_URL);
        BookStoreDTO bookFromStore = client.get().retrieve().bodyToMono(BookStoreDTO.class).block();
        return bookFromStore;
    }

    @Scheduled(cron = Constants.EXPORT_TO_EXCEL_TIME )
    public void scheduleTaskUsingCronExpression() throws IOException {
        ExportToExcel();
    }

    public void ExportToExcel() throws IOException {
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        String currentDateTime = dateFormatter.format(new Date());
        String nameFile = "book_" + currentDateTime + ".xlsx";
        List<Book> listBooks = createBookFromStore();
        BookExcelExporter excelExporter = new BookExcelExporter(listBooks);
        File file = new File(Constants.PARTH_FOLDER + nameFile);
        file.createNewFile();
        FileOutputStream outputStream = new FileOutputStream(file, false);
        excelExporter.export(outputStream);
    }

    public List<Book> createBookFromStore(){
        List<BookFromStoreDTO> listBookStore = getBookFromStore().getBooks();
        List<Book> listBook = new ArrayList<Book>();
        for(BookFromStoreDTO book:listBookStore){
            Book newBook = Book.builder()
                    .title(book.getTitle())
                    .author("BookStore")
                    .description("Book from store")
                    .image(book.getImage())
                    .subtitle(book.getSubtitle())
                    .enabled(true)
                    .subtitle(book.getSubtitle())
                    .isbn13(book.getIsbn13())
                    .price(book.getPrice())
                    .url(book.getUrl())
                    .build();
            listBook.add(newBook);
        }
        return booksRepository.saveAll(listBook);
    }
}
