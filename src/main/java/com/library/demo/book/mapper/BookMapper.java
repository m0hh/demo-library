package com.library.demo.book.mapper;

import com.library.demo.book.dtos.BookDTO;
import com.library.demo.book.entity.Book;
import com.library.demo.user.entity.User;
import com.library.demo.user.entity.UserAdminSaveDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface BookMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void mapBookDto(BookDTO dto, @MappingTarget Book entity);
}
