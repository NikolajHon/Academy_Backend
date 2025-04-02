package sk.posam.fsa.discussion.mapper;

import org.mapstruct.Mapper;
import sk.posam.fsa.discussion.User;
import sk.posam.fsa.discussion.rest.dto.CreateUserRequestDto;
import sk.posam.fsa.discussion.rest.dto.UserDto;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
    List<UserDto> toDto(Collection<User> users);
    User toEntity(CreateUserRequestDto dto);
}

