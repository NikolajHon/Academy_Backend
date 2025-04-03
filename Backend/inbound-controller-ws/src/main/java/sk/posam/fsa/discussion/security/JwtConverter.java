package sk.posam.fsa.discussion.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import sk.posam.fsa.discussion.rest.dto.UserDto;
import sk.posam.fsa.discussion.UserRole;

import java.util.*;

public class JwtConverter extends AbstractAuthenticationToken {
    private final Jwt source;

    public JwtConverter(Jwt source) {
        super(Collections.emptyList());
        this.source = source;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return Collections.emptyList();
    }

    @Override
    public Object getPrincipal() {
        UserDto userDto = new UserDto();
        userDto.setEmail(source.getClaimAsString("email"));
        userDto.setGivingName(source.getClaimAsString("given_name"));
        userDto.setRole(convertToDtoRole(getRole()));
        return userDto;
    }

    private UserRole getRole() {
        Map<String, Object> realmAccess = source.getClaimAsMap("realm_access");
        if (realmAccess == null || realmAccess.get("roles") == null) return null;

        @SuppressWarnings("unchecked")
        List<String> roles = (List<String>) realmAccess.get("roles");
        return roles.stream()
                .map(String::toUpperCase)
                .map(UserRole::valueOf)
                .findFirst()
                .orElse(null);
    }

    private UserDto.RoleEnum convertToDtoRole(UserRole role) {
        if (role == null) return null;
        return switch (role) {
            case STUDENT -> UserDto.RoleEnum.STUDENT;
            case TEACHER -> UserDto.RoleEnum.TEACHER;
        };
    }
}
