package sk.posam.fsa.discussion.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import sk.posam.fsa.discussion.User;
import sk.posam.fsa.discussion.mapper.UserMapper;
import sk.posam.fsa.discussion.rest.api.UsersApi;
import sk.posam.fsa.discussion.rest.dto.CreateUserRequestDto;
import sk.posam.fsa.discussion.rest.dto.UserDto;
import sk.posam.fsa.discussion.rest.dto.UsersResponseDto;
import sk.posam.fsa.discussion.service.UserFacade;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserRestController implements UsersApi {

    private final UserFacade userFacade;
    private final UserMapper userMapper;

    public UserRestController(UserFacade userFacade, UserMapper userMapper) {
        this.userFacade = userFacade;
        this.userMapper = userMapper;
    }

    @Override
    public ResponseEntity<Void> createUser(CreateUserRequestDto dto) {

        User user = userMapper.toEntity(dto);
        userFacade.create(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<Void> deleteUser(Long userId) {
        userFacade.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> enrollUserToCourse(Long userId, Long courseId) {
        userFacade.enrollUserToCourse(userId, courseId);
        return ResponseEntity.ok().build();
    }


    @Override
    public ResponseEntity<UsersResponseDto> getAllUsers() {
        List<User> users = new ArrayList<>(userFacade.readAll());
        List<UserDto> userDtos = userMapper.toDto(users);
        UsersResponseDto response = new UsersResponseDto();
        response.setUsers(userDtos);
        return ResponseEntity.ok(response);
    }
}
