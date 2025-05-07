package sk.posam.fsa.discussion.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import sk.posam.fsa.discussion.UserRole;
import sk.posam.fsa.discussion.rest.dto.UserDto;

import java.util.*;

public class JwtConverter extends AbstractAuthenticationToken {

    private static final Logger log = LoggerFactory.getLogger(JwtConverter.class);
    private final Jwt source;

    public JwtConverter(Jwt source) {
        super(Collections.emptyList());
        this.source = source;
        setAuthenticated(true);
        log.info("Received JWT with subject: {}", source.getSubject());
    }

    @Override
    public Object getCredentials() {
        return Collections.emptyList();
    }

    @Override
    public Object getPrincipal() {
        log.debug("Extracting principal from JWT: {}", source.getClaims());

        UserDto userDto = new UserDto();
        userDto.setEmail(source.getClaimAsString("email"));
        userDto.setGivingName(source.getClaimAsString("given_name"));

        UserRole role = getRole();
        log.info("Mapped role from JWT: {}", role);

        userDto.setRole(convertToDtoRole(role));

        return userDto;
    }

    private UserRole getRole() {
        try {
            Map<String, Object> realmAccess = source.getClaimAsMap("realm_access");
            if (realmAccess == null || realmAccess.get("roles") == null) {
                log.warn("No 'realm_access.roles' claim found in JWT");
                return null;
            }

            @SuppressWarnings("unchecked")
            List<String> roles = (List<String>) realmAccess.get("roles");
            log.debug("Roles from JWT: {}", roles);

            return roles.stream()
                    .map(String::toUpperCase)
                    .map(UserRole::valueOf)
                    .findFirst()
                    .orElse(null);
        } catch (Exception e) {
            log.error("Failed to extract role from JWT", e);
            return null;
        }
    }

    private UserDto.RoleEnum convertToDtoRole(UserRole role) {
        if (role == null) {
            log.warn("Role is null, cannot convert to DTO role");
            return null;
        }

        return switch (role) {
            case STUDENT -> UserDto.RoleEnum.STUDENT;
            case TEACHER -> UserDto.RoleEnum.TEACHER;
        };
    }
}
