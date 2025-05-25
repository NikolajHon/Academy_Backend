package sk.posam.fsa.discussion.security;

import jakarta.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import sk.posam.fsa.discussion.User;
import sk.posam.fsa.discussion.UserRole;
import sk.posam.fsa.discussion.rest.dto.UserDto;
import sk.posam.fsa.discussion.repository.CurrentUserRepository;
import sk.posam.fsa.discussion.service.UserFacade;

@Component
public class CurrentUserDetailService implements CurrentUserRepository {

    private final UserFacade userFacade;

    public CurrentUserDetailService(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    @Override
    @Transactional
    public User getCurrentUser() {

        Object principal = SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        if (!(principal instanceof UserDto dto)) {
            throw new IllegalStateException("Principal is not UserDto");
        }

        return userFacade.get(dto.getEmail())
                .orElseGet(() -> {
                    User u = new User();
                    u.setEmail(dto.getEmail());
                    u.setGivingName(dto.getGivingName());
                    u.setFamilyName(dto.getFamilyName());

                    if (dto.getRole() != null) {
                        u.setRole(dto.getRole() == UserDto.RoleEnum.TEACHER
                                ? UserRole.TEACHER
                                : UserRole.STUDENT);
                    }
                    userFacade.create(u);
                    return u;
                });
    }

    public UserDto getCurrentUserDto() {
        Object principal = SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        if (principal instanceof UserDto dto) return dto;
        throw new IllegalStateException("Principal is not UserDto");
    }
}
