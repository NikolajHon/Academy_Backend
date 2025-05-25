package sk.posam.fsa.discussion;

import java.util.Objects;

public class UserRatingId {
    private Long courseId;
    private Long userId;

    public UserRatingId() {
    }

    public UserRatingId(Long courseId, Long userId) {
        this.courseId = courseId;
        this.userId = userId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserRatingId that = (UserRatingId) o;
        return Objects.equals(courseId, that.courseId) && Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseId, userId);
    }
}
