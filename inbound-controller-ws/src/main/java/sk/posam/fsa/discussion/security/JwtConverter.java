package sk.posam.fsa.discussion.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import sk.posam.fsa.discussion.UserRole;
import sk.posam.fsa.discussion.rest.dto.UserDto;

import java.util.Collections;
import java.util.List;
import java.util.Map;

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

        UserRole role = extractRole();
        userDto.setRole(convertToDtoRole(role));

        return userDto;
    }

    private UserRole extractRole() {
        Map<String, Object> realmAccess = source.getClaimAsMap("realm_access");
        if (realmAccess == null || realmAccess.get("roles") == null) {
            return null;
        }

        @SuppressWarnings("unchecked")
        List<String> roles = (List<String>) realmAccess.get("roles");
        return roles.stream()
                .map(String::toUpperCase)
                .map(roleName -> {
                    try {
                        return UserRole.valueOf(roleName);
                    } catch (IllegalArgumentException e) {
                        return null;
                    }
                })
                .filter(r -> r != null)
                .findFirst()
                .orElse(null);
    }

    private UserDto.RoleEnum convertToDtoRole(UserRole role) {
        if (role == null) {
            return null;
        }
        return switch (role) {
            case STUDENT -> UserDto.RoleEnum.STUDENT;
            case TEACHER -> UserDto.RoleEnum.TEACHER;
        };
    }
}
