package sk.posam.fsa.discussion.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import sk.posam.fsa.discussion.UserRating;
import sk.posam.fsa.discussion.UserRatingId;
import sk.posam.fsa.discussion.repository.UserRatingRepository;

import java.util.List;

@Repository
public class JpaUserRatingRepositoryAdapter implements UserRatingRepository {

    private final UserRatingSpringDataRepository userRatingSpringDataRepository;

    public JpaUserRatingRepositoryAdapter(UserRatingSpringDataRepository userRatingSpringDataRepository) {
        this.userRatingSpringDataRepository = userRatingSpringDataRepository;
    }

    @Override
    public List<UserRating> getUserRatingsByCourse(Long courseId) {
        return userRatingSpringDataRepository.findByUserRatingIdCourseId(courseId);
    }

    @Override
    @Transactional
    public void setUserRatingsByCourse(Long courseId, Long userId, int userRating) {
        UserRatingId id = new UserRatingId(courseId, userId);

        UserRating ratingEntity = userRatingSpringDataRepository
                .findById(id)
                .orElseGet(() -> new UserRating(id));

        ratingEntity.setRating(userRating);
        userRatingSpringDataRepository.save(ratingEntity);
    }
}
