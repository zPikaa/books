package me.zpikaa.booksapi.services;

import me.zpikaa.booksapi.domain.entities.BookEntity;

import java.util.List;
import java.util.Optional;

public interface BookService {

    BookEntity save(String isbn, BookEntity book);
    List<BookEntity> findAll();
    Optional<BookEntity> findOne(String isbn);
    boolean exists(String isbn);
    BookEntity partialUpdate(String isbn, BookEntity bookEntity);

}
