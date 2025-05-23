package sk.posam.fsa.discussion.jpa;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.*;
import java.util.stream.Collectors;
public class LessonIdListConverter implements AttributeConverter<List<Long>, String> {
    private static final String SPLIT_CHAR = ",";

    @Override
    public String convertToDatabaseColumn(List<Long> attribute) {
        if (attribute == null || attribute.isEmpty()) {
            return "";
        }
        return attribute.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(SPLIT_CHAR));
    }

    @Override
    public List<Long> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.trim().isEmpty()) {
            return new ArrayList<>();
        }
        String[] parts = dbData.split(SPLIT_CHAR);
        List<Long> list = new ArrayList<>();
        for (String part : parts) {
            list.add(Long.valueOf(part));
        }
        return list;
    }
}
