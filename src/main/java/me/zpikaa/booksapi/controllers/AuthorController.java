package me.zpikaa.booksapi.controllers;

import me.zpikaa.booksapi.domain.dto.AuthorDTO;
import me.zpikaa.booksapi.domain.entities.AuthorEntity;
import me.zpikaa.booksapi.mappers.Mapper;
import me.zpikaa.booksapi.services.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthorController {

    private AuthorService authorService;
    private Mapper<AuthorEntity, AuthorDTO> authorMapper;

    public AuthorController(AuthorService authorService, Mapper<AuthorEntity, AuthorDTO> authorMapper) {
        this.authorService = authorService;
        this.authorMapper = authorMapper;
    }

    @PostMapping(path = "/authors")
    public ResponseEntity<AuthorDTO> createAuthor(@RequestBody AuthorDTO authorDTO) {
        AuthorEntity authorEntity = authorMapper.mapFrom(authorDTO);
        AuthorEntity saved = authorService.createAuthor(authorEntity);
        return new ResponseEntity<>(authorMapper.mapTo(saved), HttpStatus.CREATED);
    }

}
