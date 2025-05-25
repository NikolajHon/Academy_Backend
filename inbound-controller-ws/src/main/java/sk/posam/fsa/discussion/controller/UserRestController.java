package sk.posam.fsa.discussion.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import sk.posam.fsa.discussion.User;
import sk.posam.fsa.discussion.UserRating;
import sk.posam.fsa.discussion.mapper.UserMapper;
import sk.posam.fsa.discussion.rest.api.UsersApi;
import sk.posam.fsa.discussion.rest.dto.CreateUserRequestDto;
import sk.posam.fsa.discussion.rest.dto.SetUserRatingRequestDto;
import sk.posam.fsa.discussion.rest.dto.UserDto;
import sk.posam.fsa.discussion.rest.dto.UserRatingDto;
import sk.posam.fsa.discussion.rest.dto.UsersResponseDto;
import sk.posam.fsa.discussion.service.UserFacade;
import sk.posam.fsa.discussion.service.UserRatingFacade;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserRestController implements UsersApi {

    private final UserFacade userFacade;
    private final UserRatingFacade userRatingFacade;
    private final UserMapper userMapper;

    public UserRestController(UserFacade userFacade,
                              UserRatingFacade userRatingFacade,
                              UserMapper userMapper) {
        this.userFacade = userFacade;
        this.userRatingFacade = userRatingFacade;
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
        List<User> users = List.copyOf(userFacade.readAll());
        List<UserDto> userDtos = userMapper.toDto(users);
        UsersResponseDto response = new UsersResponseDto();
        response.setUsers(userDtos);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<List<UserRatingDto>> getUserRatingsByCourse(Long courseId) {
        List<UserRating> ratings = userRatingFacade.getUserRatingsByCourse(courseId);
        List<UserRatingDto> dtoList = ratings.stream()
                .map(r -> {
                    UserRatingDto dto = new UserRatingDto();
                    dto.setUserId(r.getUserRatingId().getUserId().toString());
                    dto.setRating(r.getRating());
                    return dto;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }


    @Override
    public ResponseEntity<Void> setUserRatingsByCourse(Long courseId,
                                                       SetUserRatingRequestDto dto) {
        Long userId = userFacade.getByKeycloakId(dto.getUserId())
                .map(u -> u.getId())
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
                );
        userRatingFacade.setUserRatingsByCourse(
                courseId,
                userId,
                dto.getRating()
        );
        return ResponseEntity.ok().build();
    }
}
