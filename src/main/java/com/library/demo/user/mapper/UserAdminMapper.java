package com.library.demo.user.mapper;


import com.library.demo.user.entity.User;
import com.library.demo.user.entity.UserAdminSaveDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface UserAdminMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void mapUserAdminToUser(UserAdminSaveDTO dto, @MappingTarget User entity);
}