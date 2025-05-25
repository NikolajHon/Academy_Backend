package sk.posam.fsa.discussion.jpa;

import org.springframework.data.repository.CrudRepository;
import sk.posam.fsa.discussion.UserRating;
import sk.posam.fsa.discussion.UserRatingId;

import java.util.List;

public interface UserRatingSpringDataRepository extends CrudRepository<UserRating, UserRatingId> {
    List<UserRating> findByUserRatingIdCourseId(Long courseId);
}
