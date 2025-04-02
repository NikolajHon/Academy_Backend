package sk.posam.fsa.discussion;

import java.util.ArrayList;
import java.util.List;

public class Course {
    private Long id;
    private String name;
    private String description;
    private List<Lesson> lessons = new ArrayList<>();

    public List<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // геттери, сеттери
}
