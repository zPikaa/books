package me.zpikaa.booksapi.services.impl;

import me.zpikaa.booksapi.domain.entities.AuthorEntity;
import me.zpikaa.booksapi.repositories.AuthorRepository;
import me.zpikaa.booksapi.services.AuthorService;
import org.springframework.stereotype.Service;

@Service
public class AuthorServiceImpl implements AuthorService {

    private AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public AuthorEntity createAuthor(AuthorEntity authorEntity) {
        return authorRepository.save(authorEntity);
    }

}
