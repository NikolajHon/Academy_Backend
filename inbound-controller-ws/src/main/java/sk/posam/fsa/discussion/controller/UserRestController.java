package sk.posam.fsa.discussion.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import sk.posam.fsa.discussion.CourseProgress;
import sk.posam.fsa.discussion.CourseProgressId;
import sk.posam.fsa.discussion.User;
import sk.posam.fsa.discussion.mapper.CourseProgressMapper;
import sk.posam.fsa.discussion.mapper.UserMapper;
import sk.posam.fsa.discussion.rest.api.UsersApi;
import sk.posam.fsa.discussion.rest.dto.*;
import sk.posam.fsa.discussion.service.CourseProgressFacade;
import sk.posam.fsa.discussion.service.UserFacade;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserRestController implements UsersApi {

    private final UserFacade userFacade;
    private final UserMapper userMapper;
    private final CourseProgressFacade courseProgressFacade;
    private final CourseProgressMapper courseProgressMapper;

    public UserRestController(UserFacade userFacade,
                              UserMapper userMapper, CourseProgressFacade courseProgressFacade, CourseProgressMapper courseProgressMapper) {
        this.userFacade = userFacade;
        this.userMapper = userMapper;
        this.courseProgressFacade = courseProgressFacade;
        this.courseProgressMapper = courseProgressMapper;
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
        userFacade.get(userId);
        CourseProgressId progressId = new CourseProgressId(courseId, userId);
        CourseProgress   progress   = new CourseProgress(progressId);
        courseProgressFacade.save(progress);

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
    public ResponseEntity<RatingDto> getUserCourseRating(String keycloakId, Long courseId) {
        Long userId = userFacade.getByKeycloakId(keycloakId)
                .map(u -> u.getId())
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
                );
        userFacade.get(userId);

        CourseProgressId progressId = new CourseProgressId(courseId, userId);
        CourseProgress progress = courseProgressFacade.findById(progressId)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                String.format("Rating for course %d and user %d not found", courseId, userId)
                        )
                );

        RatingDto dto = new RatingDto().rating(progress.getRating());
        return ResponseEntity.ok(dto);
    }


    @Override
    public ResponseEntity<List<CourseProgressWithUserDto>> listCourseProgressByCourseWithUser(Long courseId) {
        List<CourseProgress> progresses = courseProgressFacade.listCourseProgressByCourse(courseId);

        List<CourseProgressWithUserDto> dtos = progresses.stream()
                .map(progress -> {
                    CourseProgressWithUserDto dto = new CourseProgressWithUserDto();
                    CourseProgressIdDto idDto = new CourseProgressIdDto();
                    idDto.setCourseId(progress.getCourseProgressId().getCourseId());
                    idDto.setUserId(progress.getCourseProgressId().getUserId());
                    dto.setCourseProgressId(idDto);

                    dto.setLessonIds(progress.getLessonIds());
                    dto.setRating(progress.getRating());

                    User userEntity = userFacade.get(idDto.getUserId());
                    dto.setUser(userMapper.toDto(userEntity));

                    return dto;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    @Override
    public ResponseEntity<Void> setUserCourseRating(String keycloakId,
                                                    Long courseId,
                                                    RatingDto ratingDto) {
        Long userId = userFacade.getByKeycloakId(keycloakId)
                .map(u -> u.getId())
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
                );
        userFacade.get(userId);
        courseProgressFacade.setCourseRating(courseId, userId, ratingDto.getRating());
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> unenrollUserFromCourse(Long userId, Long courseId) {
        courseProgressFacade.deleteByCourseIdAndUserId(courseId, userId);
        return ResponseEntity.noContent().build();
    }


}
