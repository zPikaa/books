package me.zpikaa.booksapi.controllers;

import me.zpikaa.booksapi.domain.dto.AuthorDTO;
import me.zpikaa.booksapi.domain.entities.AuthorEntity;
import me.zpikaa.booksapi.mappers.Mapper;
import me.zpikaa.booksapi.services.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @GetMapping(path = "/authors")
    public List<AuthorDTO> listAuthors() {
        List<AuthorEntity> authors = authorService.findAll();
        return authors.stream().map(authorMapper::mapTo).toList();
    }

    @GetMapping(path = "/authors/{id}")
    public ResponseEntity<AuthorDTO> getAuthor(@PathVariable Long id) {
        Optional<AuthorEntity> foundAuthor = authorService.findOne(id);

        return foundAuthor.map(authorEntity -> {
            AuthorDTO authorDTO = authorMapper.mapTo(authorEntity);
            return new ResponseEntity<>(authorDTO, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
