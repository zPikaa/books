package me.zpikaa.booksapi.mappers.impl;

import me.zpikaa.booksapi.domain.dto.BookDTO;
import me.zpikaa.booksapi.domain.entities.BookEntity;
import me.zpikaa.booksapi.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class BookMapper implements Mapper<BookEntity, BookDTO> {

    private ModelMapper modelMapper;

    public BookMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public BookDTO mapTo(BookEntity entity) {
        return modelMapper.map(entity, BookDTO.class);
    }

    @Override
    public BookEntity mapFrom(BookDTO dto) {
        return modelMapper.map(dto, BookEntity.class);
    }

}
