package me.zpikaa.booksapi.services;

import me.zpikaa.booksapi.domain.entities.BookEntity;

import java.util.List;

public interface BookService {

    BookEntity createBook(String isbn, BookEntity book);

    List<BookEntity> findAll();

}
