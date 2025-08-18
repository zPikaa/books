package me.zpikaa.booksapi.controllers;

import me.zpikaa.booksapi.domain.dto.BookDTO;
import me.zpikaa.booksapi.domain.entities.BookEntity;
import me.zpikaa.booksapi.mappers.Mapper;
import me.zpikaa.booksapi.services.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @GetMapping(path = "/books")
    public List<BookDTO> listBooks() {
        List<BookEntity> books = bookService.findAll();
        return books.stream().map(bookMapper::mapTo).toList();
    }

    @GetMapping(path = "/books/{isbn}")
    public ResponseEntity<BookDTO> getBook(@PathVariable String isbn) {
        Optional<BookEntity> foundBook = bookService.findOne(isbn);

        return foundBook.map(bookEntity -> {
            BookDTO bookDTO = bookMapper.mapTo(bookEntity);
            return new ResponseEntity<>(bookDTO, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
