package sk.posam.fsa.discussion.jpa;


import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import sk.posam.fsa.discussion.UserRole;

@Converter(autoApply = true)
public class UserRoleConverter implements AttributeConverter<UserRole, String> {

    @Override
    public String convertToDatabaseColumn(UserRole role) {
        if (role == null) return null;
        return role.name();
    }

    @Override
    public UserRole convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;
        return UserRole.valueOf(dbData.toUpperCase());
    }
}
