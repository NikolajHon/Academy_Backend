package sk.posam.fsa.discussion;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CourseProgress {

    private CourseProgressId courseProgressId;
    private List<Long> lessonIds = new ArrayList<>();
    private int rating;
    public CourseProgress(CourseProgressId courseProgressId) {
        this.courseProgressId = courseProgressId;
    }

    public CourseProgress() {
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CourseProgress that = (CourseProgress) o;
        return rating == that.rating && Objects.equals(courseProgressId, that.courseProgressId) && Objects.equals(lessonIds, that.lessonIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseProgressId, lessonIds, rating);
    }

    public CourseProgressId getCourseProgressId() {
        return courseProgressId;
    }

    public void setCourseProgressId(CourseProgressId courseProgressId) {
        this.courseProgressId = courseProgressId;
    }

    public List<Long> getLessonIds() {
        return lessonIds;
    }

    public void setLessonIds(List<Long> lessonIds) {
        this.lessonIds = lessonIds;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
