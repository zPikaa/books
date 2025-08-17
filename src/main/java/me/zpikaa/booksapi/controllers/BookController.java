package me.zpikaa.booksapi.controllers;

import me.zpikaa.booksapi.domain.dto.BookDTO;
import me.zpikaa.booksapi.domain.entities.BookEntity;
import me.zpikaa.booksapi.mappers.Mapper;
import me.zpikaa.booksapi.services.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {

    private BookService bookService;
    private Mapper<BookEntity, BookDTO> bookMapper;

    public BookController(BookService bookService, Mapper<BookEntity, BookDTO> bookMapper) {
        this.bookService = bookService;
        this.bookMapper = bookMapper;
    }

    @PutMapping(path = "/books/{isbn}")
    public ResponseEntity<BookDTO> createBook(@PathVariable String isbn, @RequestBody BookDTO bookDTO) {
        BookEntity bookEntity = bookMapper.mapFrom(bookDTO);
        BookEntity saved = bookService.createBook(isbn, bookEntity);
        return new ResponseEntity<>(bookMapper.mapTo(saved), HttpStatus.CREATED);
    }

}
