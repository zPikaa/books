package me.zpikaa.booksapi.services;

import me.zpikaa.booksapi.domain.entities.AuthorEntity;

import java.util.List;
import java.util.Optional;

public interface AuthorService {

    AuthorEntity save(AuthorEntity authorEntity);
    List<AuthorEntity> findAll();
    Optional<AuthorEntity> findOne(Long id);
    boolean exists(Long id);
    AuthorEntity partialUpdate(Long id, AuthorEntity authorEntity);

}
