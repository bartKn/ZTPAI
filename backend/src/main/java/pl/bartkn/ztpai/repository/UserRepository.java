package pl.bartkn.ztpai.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.bartkn.ztpai.model.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmailOrUsername(String email, String username);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    Optional<User> findUserByEmail(String email);
    Optional<User> findUserByUsername(String username);
    List<User> findAllByUsernameContains(String username);
    User getUserByEmail(String email);
}
