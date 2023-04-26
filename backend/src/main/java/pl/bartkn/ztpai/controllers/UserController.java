package pl.bartkn.ztpai.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pl.bartkn.ztpai.model.dto.request.UserId;
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

    @GetMapping("/api/users")
    public List<UserInfo> getUsers(@RequestParam Optional<String> username) {
        return userService.getAllUsers(username);
    }

    @GetMapping("/api/users/{username}")
    public User getUserByUsername(@PathVariable String username) {
        return userService.getUserByUsername(username);
    }

    @GetMapping("/api/users/friends")
    public Set<User> getFriends() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.findUserFriends(email);
    }

    @PostMapping("/api/users/friends/add")
    public ResponseEntity<?> addFriend(@RequestBody @Valid UserId id) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        userService.addFriend(email, id.getId());
        return ResponseEntity.ok().build();
    }
}
