package sk.posam.fsa.discussion.repository;

import sk.posam.fsa.discussion.UserRating;

import java.util.List;

public interface UserRatingRepository {
    List<UserRating> getUserRatingsByCourse(Long courseId);
    void setUserRatingsByCourse(Long courseId, Long userId, int userRating);
}
