package me.zpikaa.booksapi.mappers.impl;

import me.zpikaa.booksapi.domain.dto.AuthorDTO;
import me.zpikaa.booksapi.domain.entities.AuthorEntity;
import me.zpikaa.booksapi.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapper implements Mapper<AuthorEntity, AuthorDTO> {

    private ModelMapper modelMapper;

    public AuthorMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public AuthorDTO mapTo(AuthorEntity entity) {
        return modelMapper.map(entity, AuthorDTO.class);
    }

    @Override
    public AuthorEntity mapFrom(AuthorDTO dto) {
        return modelMapper.map(dto, AuthorEntity.class);
    }

}
