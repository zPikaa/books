package me.zpikaa.booksapi.services.impl;

import me.zpikaa.booksapi.domain.entities.BookEntity;
import me.zpikaa.booksapi.repositories.BookRepository;
import me.zpikaa.booksapi.services.BookService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.StreamSupport;

@Service
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public BookEntity createBook(String isbn, BookEntity book) {
        book.setIsbn(isbn);
        return bookRepository.save(book);
    }

    @Override
    public List<BookEntity> findAll() {
        return StreamSupport.stream(bookRepository.findAll().spliterator(), false).toList();
    }

}
