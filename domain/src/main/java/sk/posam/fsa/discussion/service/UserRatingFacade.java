package sk.posam.fsa.discussion.service;

import sk.posam.fsa.discussion.Course;
import sk.posam.fsa.discussion.UserRating;

import java.util.List;

public interface UserRatingFacade {
    List<UserRating> getUserRatingsByCourse(Long courseId);
    void setUserRatingsByCourse(Long courseId, Long userId, int userRating);

}
