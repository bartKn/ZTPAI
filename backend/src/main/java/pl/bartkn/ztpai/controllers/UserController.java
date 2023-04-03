package pl.bartkn.ztpai.controllers;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.bartkn.ztpai.model.dto.request.LoginRequest;
import pl.bartkn.ztpai.model.dto.request.RegisterRequest;
import pl.bartkn.ztpai.model.dto.response.AuthResponse;
import pl.bartkn.ztpai.model.dto.response.UserInfo;
import pl.bartkn.ztpai.model.entity.User;
import pl.bartkn.ztpai.service.UserService;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest userCredentials) {
        return ResponseEntity.ok().body(userService.login(userCredentials));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest userCredentials) {
        userService.register(userCredentials);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/users/delete")
    public ResponseEntity<?> deleteAccount() {
        String username = "test";
        userService.deleteUser(username);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/users")
    public List<UserInfo> getUsers(@RequestParam Optional<String> username) {
        return userService.getAllUsers(username);
    }

    @GetMapping("/users/{username}")
    public User getUserByUsername(@PathVariable String username) {
        return userService.getUserByUsername(username);
    }

    @GetMapping("/users/friends")
    public Set<User> getFriends() {
        String username = "test";
        return userService.findUserFriends(username);
    }

    @PostMapping("/users/friends/add")
    public ResponseEntity<?> addFriend() {
        String username = "test";
        long id = 3;
        userService.addFriend(username, id);
        System.out.println("dodano");
        return ResponseEntity.ok().build();
    }
}
