package sk.posam.fsa.discussion.security;


import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import sk.posam.fsa.discussion.User;
import sk.posam.fsa.discussion.service.UserFacade;
import sk.posam.fsa.discussion.rest.dto.UserDto;

@Service
public class CurrentUserDetailService {

    private final UserFacade userFacade;

    public CurrentUserDetailService(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    public UserDto getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDto userDto) {
            return userDto;
        }

        throw new IllegalStateException("Current user is not of type UserDto.");
    }

    public User getFullCurrentUser() {
        return userFacade.get(getCurrentUser().getEmail()).orElseThrow();
    }
}
