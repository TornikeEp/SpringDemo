package com.example.SpringDemo.controller;

import com.example.SpringDemo.model.Book;
import com.example.SpringDemo.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<Book>> getBooks() {
        List<Book> books = bookService.getBooks();
        if (books.size() > 0) {
            return new ResponseEntity<>(books, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deletePost(@PathVariable Long id) {
        if (!bookService.isDeleted(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Book> postBook(@RequestBody Book book, HttpServletRequest request) {
        Long bookId = bookService.saveBook(book);
        try {
            return ResponseEntity.created(new URI(request.getRequestURL().append("/").append(bookId.toString()).toString())).build();
        } catch (URISyntaxException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book book,
                                           HttpServletRequest request) {
        Book persistedBook = bookService.getBook(id);
        if (persistedBook == null) {
            Long bookId = bookService.saveBook(book);
            try {
                return ResponseEntity.created(new URI(request.getRequestURL().append("/")
                        .append(bookId.toString()).toString())).build();
            } catch (URISyntaxException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
        return new ResponseEntity(bookService.updateBook(id, book), HttpStatus.OK);
    }
}
