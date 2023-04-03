package pl.bartkn.ztpai.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.bartkn.ztpai.exception.EmailOrUsernameTakenException;
import pl.bartkn.ztpai.exception.UserNotFoundException;
import pl.bartkn.ztpai.model.dto.request.LoginRequest;
import pl.bartkn.ztpai.model.dto.request.RegisterRequest;
import pl.bartkn.ztpai.model.dto.response.AuthResponse;
import pl.bartkn.ztpai.model.dto.response.UserInfo;
import pl.bartkn.ztpai.model.entity.User;
import pl.bartkn.ztpai.model.mapper.UserMapper;
import pl.bartkn.ztpai.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );
        List<String> userRoles = new ArrayList<>();
        User user = userRepository.getUserByEmail(loginRequest.getEmail());
        user.getAuthorities().forEach(role -> userRoles.add(role.getAuthority()));
        return new AuthResponse(userRoles);
    }

    public void register(RegisterRequest registerRequest) {
        if (userRepository.existsByEmailOrUsername(
                registerRequest.getEmail(),
                registerRequest.getUsername()
        )) {
            throw new EmailOrUsernameTakenException();
        }
        User user = userMapper.map(registerRequest);
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        userRepository.save(user);
    }

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

    public Set<User> findUserFriends(String username) {
        User user = userRepository.findUserByUsername(username).get();
        return user.getFriends();
    }

    public void addFriend(String username, Long id) {
        User user = userRepository.findUserByUsername(username).get();
        User friend = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        System.out.println(user.getUsername());
        System.out.println(friend.getUsername());
        user.getFriends().add(friend);
        System.out.println(user);
        userRepository.save(user);
    }

    public void deleteUser(String username) {
        User user = userRepository.findUserByUsername(username).get();
        userRepository.delete(user);
    }
}
