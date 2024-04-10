package com.library.demo.patron.mapper;

import com.library.demo.book.dtos.BookDTO;
import com.library.demo.book.entity.Book;
import com.library.demo.patron.dtos.PatronDTO;
import com.library.demo.patron.entity.Patron;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface PatronMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void mapPatronDto(PatronDTO dto, @MappingTarget Patron entity);
}



