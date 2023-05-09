package pl.bartkn.ztpai.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.bartkn.ztpai.exception.EmailOrUsernameTakenException;
import pl.bartkn.ztpai.exception.UserNotFoundException;
import pl.bartkn.ztpai.model.dto.response.UserAccountDetails;
import pl.bartkn.ztpai.model.dto.response.UserInfo;
import pl.bartkn.ztpai.model.entity.User;
import pl.bartkn.ztpai.model.mapper.UserMapper;
import pl.bartkn.ztpai.repository.UserRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
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

    public Set<User> findUserFriends(String email) {
        User user = userRepository.findUserByEmail(email).orElseThrow(UserNotFoundException::new);
        return user.getFriends();
    }

    public void addFriend(String email, Long id) {
        User user = userRepository.findUserByEmail(email).orElseThrow(UserNotFoundException::new);
        User friend = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        user.getFriends().add(friend);
        friend.getFriends().add(user);
        userRepository.save(user);
        userRepository.save(friend);
    }

    public UserAccountDetails getUserDetails(User user) {
        return UserAccountDetails.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .balance(user.getBalance())
                .build();
    }

    public UserAccountDetails updateEmail(User user, String email) {
        if (userRepository.existsByEmail(email)) {
            throw new EmailOrUsernameTakenException("Email is taken!");
        }
        user.setEmail(email);
        userRepository.save(user);
        return getUserDetails(user);
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
