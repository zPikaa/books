package me.zpikaa.booksapi.controllers;

import me.zpikaa.booksapi.domain.dto.AuthorDTO;
import me.zpikaa.booksapi.domain.entities.AuthorEntity;
import me.zpikaa.booksapi.mappers.Mapper;
import me.zpikaa.booksapi.services.AuthorService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class AuthorController {

    private final AuthorService authorService;
    private final Mapper<AuthorEntity, AuthorDTO> authorMapper;

    public AuthorController(AuthorService authorService, Mapper<AuthorEntity, AuthorDTO> authorMapper) {
        this.authorService = authorService;
        this.authorMapper = authorMapper;
    }

    @PostMapping(path = "/authors")
    public ResponseEntity<AuthorDTO> createAuthor(@RequestBody AuthorDTO authorDTO) {
        AuthorEntity authorEntity = authorMapper.mapFrom(authorDTO);
        AuthorEntity saved = authorService.save(authorEntity);
        return new ResponseEntity<>(authorMapper.mapTo(saved), HttpStatus.CREATED);
    }

    @GetMapping(path = "/authors")
    public Page<AuthorDTO> listAuthors(Pageable pageable) {
        Page<AuthorEntity> authors = authorService.findAll(pageable);
        return authors.map(authorMapper::mapTo);
    }

    @GetMapping(path = "/authors/{id}")
    public ResponseEntity<AuthorDTO> getAuthor(@PathVariable Long id) {
        Optional<AuthorEntity> foundAuthor = authorService.findOne(id);

        return foundAuthor.map(authorEntity -> {
            AuthorDTO authorDTO = authorMapper.mapTo(authorEntity);
            return new ResponseEntity<>(authorDTO, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping(path = "/authors/{id}")
    public ResponseEntity<AuthorDTO> fullUpdateAuthor(@PathVariable Long id, @RequestBody AuthorDTO authorDTO) {
        if (!authorService.exists(id))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        authorDTO.setId(id);
        AuthorEntity authorEntity = authorMapper.mapFrom(authorDTO);

        AuthorEntity saved = authorService.save(authorEntity);
        return new ResponseEntity<>(authorMapper.mapTo(saved), HttpStatus.OK);
    }

    @PatchMapping(path = "/authors/{id}")
    public ResponseEntity<AuthorDTO> partialUpdateAuthor(@PathVariable Long id, @RequestBody AuthorDTO authorDTO) {
        if (!authorService.exists(id))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        AuthorEntity authorEntity = authorMapper.mapFrom(authorDTO);
        AuthorEntity updatedEntity = authorService.partialUpdate(id, authorEntity);

        return new ResponseEntity<>(authorMapper.mapTo(updatedEntity), HttpStatus.OK);
    }

    @DeleteMapping(path = "/authors/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAuthor(@PathVariable Long id) {
        authorService.delete(id);
    }

}
