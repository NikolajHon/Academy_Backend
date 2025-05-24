package sk.posam.fsa.discussion.jpa;

import org.springframework.stereotype.Repository;
import sk.posam.fsa.discussion.User;
import sk.posam.fsa.discussion.repository.UserRepository;

import java.util.Collection;
import java.util.Optional;

@Repository
public class JpaUserRepositoryAdapter implements UserRepository {

    private final UserSpringDataRepository userSpringDataRepository;

    public JpaUserRepositoryAdapter(UserSpringDataRepository userSpringDataRepository) {
        this.userSpringDataRepository = userSpringDataRepository;
    }

    @Override
    public User get(long id) {
        return userSpringDataRepository.getReferenceById(id);
    }

    @Override
    public Optional<User> get(String email) {
        return userSpringDataRepository.findByEmail(email);
    }

    @Override
    public Collection<User> readAll() {
        return userSpringDataRepository.findAll();
    }

    @Override
    public void create(User user) {
        userSpringDataRepository.save(user);
    }

    @Override
    public void delete(long id) {

        userSpringDataRepository.deleteById(id);
    }

}
