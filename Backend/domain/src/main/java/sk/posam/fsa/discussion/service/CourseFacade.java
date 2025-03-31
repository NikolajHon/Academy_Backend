package sk.posam.fsa.discussion.service;

import sk.posam.fsa.discussion.Course;

import java.util.Collection;

public interface CourseFacade {

    Collection<Course> readAll();

    void create(Course course);
}
