package sk.posam.fsa.discussion.service;

import sk.posam.fsa.discussion.UserRating;
import sk.posam.fsa.discussion.repository.UserRatingRepository;

import java.util.List;

public class UserRatingService implements UserRatingFacade{

    private final UserRatingRepository userRatingRepository;

    public UserRatingService(UserRatingRepository userRatingRepository) {
        this.userRatingRepository = userRatingRepository;
    }

    @Override
    public List<UserRating> getUserRatingsByCourse(Long courseId) {
        return userRatingRepository.getUserRatingsByCourse(courseId);
    }

    @Override
    public void setUserRatingsByCourse(Long courseId, Long userId, int userRating) {
        userRatingRepository.setUserRatingsByCourse(courseId, userId, userRating);
    }
}
