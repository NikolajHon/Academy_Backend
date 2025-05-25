package sk.posam.fsa.discussion;

import java.util.ArrayList;
import java.util.List;

public class User {
    private Long id;
    private String givingName;
    private String familyName;
    private String email;
    private UserRole role;
    private String keycloakId;
    private List<Course> courses;
    private List<Post> posts = new ArrayList<>();

    public String getKeycloakId() {
        return keycloakId;
    }

    public void setKeycloakId(String keycloakId) {
        this.keycloakId = keycloakId;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public String getGivingName() { return givingName; }
    public List<Course> getCourses() { return courses; }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public void setGivingName(String givingName) {
        this.givingName = givingName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}
