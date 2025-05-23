package sk.posam.fsa.discussion;

import java.io.Serializable;
import java.util.Objects;

public class CourseProgressId implements Serializable {
    private Long courseId;
    private Long userId;

    public CourseProgressId() {}

    public CourseProgressId(Long courseId, Long userId) {
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
        if (this == o) return true;
        if (!(o instanceof CourseProgressId)) return false;
        CourseProgressId that = (CourseProgressId) o;
        return Objects.equals(courseId, that.courseId)
                && Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseId, userId);
    }
}