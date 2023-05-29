package pl.bartkn.ztpai.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.bartkn.ztpai.model.entity.Friend;
import pl.bartkn.ztpai.model.entity.User;

import java.util.List;

@Repository
public interface FriendsRepository extends JpaRepository<Friend, Long> {
    boolean existsByFirstUserAndSecondUser(User first, User second);
    List<Friend> findByFirstUser(User user);
}
