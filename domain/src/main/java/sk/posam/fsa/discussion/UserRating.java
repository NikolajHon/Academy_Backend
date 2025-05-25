package sk.posam.fsa.discussion;

import java.util.Objects;

public class UserRating {
    private UserRatingId userRatingId;
    private int rating;

    public UserRating(UserRatingId userRatingId) {
        this.userRatingId = userRatingId;
    }

    public UserRating() {
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserRating that = (UserRating) o;
        return rating == that.rating && Objects.equals(userRatingId, that.userRatingId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userRatingId, rating);
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public UserRatingId getUserRatingId() {
        return userRatingId;
    }

    public void setUserRatingId(UserRatingId userRatingId) {
        this.userRatingId = userRatingId;
    }
}
