package sk.posam.fsa.discussion.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.posam.fsa.discussion.User;

import java.util.Optional;

public interface UserSpringDataRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}

