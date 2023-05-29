package pl.bartkn.ztpai.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.bartkn.ztpai.exception.EmailOrUsernameTakenException;
import pl.bartkn.ztpai.exception.UserNotFoundException;
import pl.bartkn.ztpai.model.dto.response.user.UserAccountDetails;
import pl.bartkn.ztpai.model.dto.response.user.UserInfo;
import pl.bartkn.ztpai.model.entity.Friend;
import pl.bartkn.ztpai.model.entity.User;
import pl.bartkn.ztpai.model.mapper.UserMapper;
import pl.bartkn.ztpai.repository.FriendsRepository;
import pl.bartkn.ztpai.repository.UserRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final FriendsRepository friendsRepository;
    private final UserMapper userMapper;

    public List<UserInfo> getAllUsers(Optional<String> username) {
        return username.map(s -> userRepository.findAllByUsernameContains(s)
                .stream()
                .map(userMapper::map)
                .collect(Collectors.toList()))
                .orElseGet(() -> userRepository.findAll()
                .stream()
                .map(userMapper::map)
                .collect(Collectors.toList()));
    }

    public User getUserByUsername(String username) {
        return userRepository.findUserByUsername(username).orElse(null);
    }

    public List<UserInfo> findUserFriends(String email) {
        User user = userRepository.findUserByEmail(email).orElseThrow(UserNotFoundException::new);
        return friendsRepository.findByFirstUser(user)
                .stream()
                .map(Friend::getSecondUser)
                .map(userMapper::map)
                .collect(Collectors.toList());
    }

    public void addFriend(String email, Long id) {
        User firstUser = userRepository.findUserByEmail(email).orElseThrow(UserNotFoundException::new);
        User secondUser = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        if (friendsRepository.existsByFirstUserAndSecondUser(firstUser, secondUser)) {
            return;
        }
        saveFriendEntity(firstUser, secondUser);
        saveFriendEntity(secondUser, firstUser);
    }

    private void saveFriendEntity(User u1, User u2) {
        Friend friend = new Friend();
        friend.setFirstUser(u1);
        friend.setSecondUser(u2);
        friendsRepository.save(friend);
    }

    public UserAccountDetails getUserDetails(User user) {
        return UserAccountDetails.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsernameValue())
                .balance(user.getBalance())
                .build();
    }

    public UserAccountDetails updateUsername(User user, String username) {
        if (userRepository.existsByUsername(username)) {
            throw new EmailOrUsernameTakenException("Username is taken!");
        }
        user.setUsername(username);
        userRepository.save(user);
        return getUserDetails(user);
    }

    public UserAccountDetails updateBalance(User user, BigDecimal balance) {
        user.setBalance(user.getBalance().add(balance));
        userRepository.save(user);
        return getUserDetails(user);
    }
}
