package me.zpikaa.booksapi.services;

import me.zpikaa.booksapi.domain.entities.AuthorEntity;

import java.util.List;

public interface AuthorService {

    AuthorEntity createAuthor(AuthorEntity authorEntity);
    List<AuthorEntity> findAll();

}
