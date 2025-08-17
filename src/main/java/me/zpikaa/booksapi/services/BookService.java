package me.zpikaa.booksapi.services;

import me.zpikaa.booksapi.domain.entities.BookEntity;

public interface BookService {

    BookEntity createBook(String isbn, BookEntity book);

}
